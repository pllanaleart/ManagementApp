package com.managementapp.managementapplication.service.serviceImpl;

import com.managementapp.managementapplication.entity.ExpensesEntity;
import com.managementapp.managementapplication.entity.PurchaseProductEntity;
import com.managementapp.managementapplication.entity.PurchasesEntity;
import com.managementapp.managementapplication.entity.StockEntity;
import com.managementapp.managementapplication.repository.ExpensesRepository;
import com.managementapp.managementapplication.repository.PurchaseProductRepository;
import com.managementapp.managementapplication.repository.PurchasesRepository;
import com.managementapp.managementapplication.repository.StockRepository;
import com.managementapp.managementapplication.service.PurchasesService;
import com.managementapp.managementapplication.shared.Mapper.PurchaseMapper;
import com.managementapp.managementapplication.shared.Mapper.PurchaseResponseMapper;
import com.managementapp.managementapplication.shared.dto.PurchaseProductDto;
import com.managementapp.managementapplication.shared.dto.PurchasesDto;
import com.managementapp.managementapplication.ui.response.purchasesResponse.PurchaseResponseModel;
import com.managementapp.managementapplication.ui.response.purchasesResponse.PurchasesPagedResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Service
public class PurchasesServiceImpl implements PurchasesService {

    private PurchasesRepository purchasesRepository;
    private PurchaseProductRepository purchaseProductRepository;
    private StockRepository stockRepository;
    private ExpensesRepository expensesRepository;

    @Autowired
    public PurchasesServiceImpl(PurchasesRepository purchasesRepository, PurchaseProductRepository purchaseProductRepository, StockRepository stockRepository, ExpensesRepository expensesRepository) {
        this.purchasesRepository = purchasesRepository;
        this.purchaseProductRepository = purchaseProductRepository;
        this.stockRepository = stockRepository;
        this.expensesRepository = expensesRepository;
    }

    @Override
    public PurchasesPagedResponseModel getAll(int page, int limit, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page,limit,sort);
        Page<PurchasesEntity> purchasesEntities = purchasesRepository.findAll(pageable);
        List<PurchasesEntity> purchasesEntityList = purchasesEntities.getContent();
        List<PurchaseResponseModel> purchaseResponseModelList = new ArrayList<>();
        for (PurchasesEntity purchase : purchasesEntityList) {
            PurchasesDto purchasesDto = new PurchasesDto();
            PurchaseMapper.combineToDto(purchasesDto,purchase);
           PurchaseResponseModel purchaseResponseModel = PurchaseResponseMapper.createPurchaseResponseModel(purchasesDto);
            purchaseResponseModelList.add(purchaseResponseModel);
        }
        return new PurchasesPagedResponseModel(purchaseResponseModelList,page,limit,purchasesEntities.getTotalElements(),
                purchasesEntities.getTotalPages(),purchasesEntities.isLast());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PurchasesDto createPurchase(PurchasesDto purchasesDto) {
        PurchasesEntity purchases = new PurchasesEntity();
        Set<PurchaseProductEntity> purchaseProductEntitiesSet = new HashSet<>();
        purchases = purchasesRepository.save(purchases);
        PurchaseMapper.combineToEntity(purchases, purchasesDto);
        if (purchases.getProducts() == null) throw new RuntimeException("Invoice is blank");
        for (PurchaseProductEntity purchaseProductEntity : purchases.getProducts()) {
            StockEntity stock = stockRepository.findByProductsId(purchaseProductEntity.getProducts().getId());
            if (stock == null) throw new RuntimeException("Product not found in stock");
            purchaseProductEntity.setPurchases(purchases);
            purchaseProductEntity.setProducts(stock.getProductsEntity());
            stock.setQuantity(stock.getQuantity() + purchaseProductEntity.getQuantity().intValue());
            stockRepository.save(stock);
            purchaseProductEntitiesSet.add(purchaseProductRepository.save(purchaseProductEntity));
        }
        purchases.setProducts(purchaseProductEntitiesSet);
        PurchasesDto returnValue = new PurchasesDto();
        PurchaseMapper.combineToDto(returnValue, purchases);
        ExpensesEntity expensesEntity = new ExpensesEntity();
        double totalvalue = 0;
        for (PurchaseProductDto purchaseProductDto : returnValue.getProducts()) {
            totalvalue = totalvalue + (purchaseProductDto.getBuyPrice() * purchaseProductDto.getQuantity());
        }
        expensesEntity.setDate(returnValue.getDate());
        expensesEntity.setDescription(returnValue.getDescription());
        expensesEntity.setPurchasesId(purchases);
        expensesEntity.setTotalValue(new BigDecimal(totalvalue).setScale(2, RoundingMode.HALF_UP).doubleValue());
        expensesRepository.save(expensesEntity);
        return returnValue;
    }

    @Override
    public PurchasesDto findPurchaseById(Long id) {
        Optional<PurchasesEntity> purchasesEntity = purchasesRepository.findById(id);
        PurchasesDto returnValue = new PurchasesDto();
        if(purchasesEntity.isPresent()){
            PurchaseMapper.combineToDto(returnValue,purchasesEntity.get());
        }else throw new RuntimeException("Purchase not found");
        return returnValue;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PurchasesDto updatePurchase(PurchasesDto purchasesDto) {
        Optional<PurchasesEntity> purchases = purchasesRepository.findById(purchasesDto.getId());
        PurchasesEntity updatedPurchase = new PurchasesEntity();
        Set<PurchaseProductEntity> purchaseProductEntitiesSet = new HashSet<>();
        if(purchases.isPresent()){
            updatedPurchase = purchases.get();
            for (PurchaseProductEntity productEntity: updatedPurchase.getProducts()){
                StockEntity stockEntity = stockRepository.findByProductsId(productEntity.getProducts().getId());
                int stockQuantity = stockEntity.getQuantity()-productEntity.getQuantity().intValue();
                stockEntity.setQuantity(stockQuantity);
                stockRepository.save(stockEntity);
                purchaseProductRepository.delete(productEntity);
            }
        }else throw new RuntimeException("No purchase found");
        PurchaseMapper.combineToEntity(updatedPurchase, purchasesDto);
        if (updatedPurchase.getProducts() == null) throw new RuntimeException("Invoice is blank");
        for (PurchaseProductEntity purchaseProductEntity:updatedPurchase.getProducts()){
            StockEntity stock = stockRepository.findByProductsId(purchaseProductEntity.getProducts().getId());
            if (stock == null) throw new RuntimeException("Product not found in stock");
            purchaseProductEntity.setPurchases(updatedPurchase);
            purchaseProductEntity.setProducts(stock.getProductsEntity());
            stock.setQuantity(stock.getQuantity() + purchaseProductEntity.getQuantity().intValue());
            stockRepository.save(stock);
            purchaseProductEntitiesSet.add(purchaseProductRepository.save(purchaseProductEntity));
        }
        updatedPurchase.setProducts(purchaseProductEntitiesSet);
        purchasesRepository.save(updatedPurchase);
        PurchasesDto returnValue = new PurchasesDto();
        PurchaseMapper.combineToDto(returnValue,updatedPurchase);
        ExpensesEntity expensesEntity = expensesRepository.findByPurchasesId(updatedPurchase);
        if(expensesEntity == null)throw new RuntimeException("Expense not found");
        expensesEntity.setDescription(returnValue.getDescription());
        double totalValue = 0;
        for (PurchaseProductDto purchaseProductDto : returnValue.getProducts()){
            totalValue = totalValue + (purchaseProductDto.getBuyPrice()*purchaseProductDto.getQuantity());
        }
        expensesEntity.setTotalValue(totalValue);
        expensesRepository.save(expensesEntity);
        return returnValue;
    }
}
