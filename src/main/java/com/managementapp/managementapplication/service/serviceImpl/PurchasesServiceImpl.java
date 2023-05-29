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
import com.managementapp.managementapplication.shared.dto.PurchaseProductDto;
import com.managementapp.managementapplication.shared.dto.PurchasesDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

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
}
