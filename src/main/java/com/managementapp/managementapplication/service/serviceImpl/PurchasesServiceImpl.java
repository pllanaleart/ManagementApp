package com.managementapp.managementapplication.service.serviceImpl;

import com.managementapp.managementapplication.entity.PurchaseProductEntity;
import com.managementapp.managementapplication.entity.PurchasesEntity;
import com.managementapp.managementapplication.entity.StockEntity;
import com.managementapp.managementapplication.repository.PurchaseProductRepository;
import com.managementapp.managementapplication.repository.PurchasesRepository;
import com.managementapp.managementapplication.repository.StockRepository;
import com.managementapp.managementapplication.service.PurchasesService;
import com.managementapp.managementapplication.shared.Mapper.PurchaseMapper;
import com.managementapp.managementapplication.shared.dto.PurchaseProductDto;
import com.managementapp.managementapplication.shared.dto.PurchasesDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
public class PurchasesServiceImpl implements PurchasesService {

    private PurchasesRepository purchasesRepository;
    private PurchaseProductRepository purchaseProductRepository;
    private StockRepository stockRepository;
    private ModelMapper mapper = new ModelMapper();
    @Autowired
    public PurchasesServiceImpl(PurchasesRepository purchasesRepository, PurchaseProductRepository purchaseProductRepository, StockRepository stockRepository) {
        this.purchasesRepository = purchasesRepository;
        this.purchaseProductRepository = purchaseProductRepository;
        this.stockRepository = stockRepository;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PurchasesDto createPurchase(PurchasesDto purchasesDto) {
        PurchasesEntity purchases = new PurchasesEntity();
        Set<PurchaseProductEntity> purchaseProductEntitiesSet = new HashSet<>();
        purchases = purchasesRepository.save(purchases);
        PurchaseMapper.combineToEntity(purchases,purchasesDto);
        for (PurchaseProductEntity purchaseProductEntity: purchases.getProducts()){
            StockEntity stock =stockRepository.findByProductsId(purchaseProductEntity.getProducts().getId());
            purchaseProductEntity.setPurchases(purchases);
            purchaseProductEntity.setProducts(stock.getProductsEntity());
            stock.setQuantity(stock.getQuantity()+purchaseProductEntity.getQuantity().intValue());
            stockRepository.save(stock);
            purchaseProductEntitiesSet.add(purchaseProductRepository.save(purchaseProductEntity));
        }
        purchases.setProducts(purchaseProductEntitiesSet);
        PurchasesDto returnValue= new PurchasesDto();
        PurchaseMapper.combineToDto(returnValue,purchases);
        return returnValue;
    }
}
