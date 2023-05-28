package com.managementapp.managementapplication.service.serviceImpl;

import com.managementapp.managementapplication.entity.PurchaseProductEntity;
import com.managementapp.managementapplication.entity.PurchasesEntity;
import com.managementapp.managementapplication.repository.PurchaseProductRepository;
import com.managementapp.managementapplication.repository.PurchasesRepository;
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
    private ModelMapper mapper = new ModelMapper();
    @Autowired
    public PurchasesServiceImpl(PurchasesRepository purchasesRepository, PurchaseProductRepository purchaseProductRepository) {
        this.purchasesRepository = purchasesRepository;
        this.purchaseProductRepository = purchaseProductRepository;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PurchasesDto createPurchase(PurchasesDto purchasesDto) {
        PurchasesEntity purchases = new PurchasesEntity();
        purchasesRepository.save(purchases);
        PurchaseMapper.combineToEntity(purchases,purchasesDto);

        return null;
    }
}
