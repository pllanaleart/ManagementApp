package com.managementapp.managementapplication.service;

import com.managementapp.managementapplication.shared.dto.PurchasesDto;

public interface PurchasesService {


    PurchasesDto createPurchase(PurchasesDto purchasesDto);
    PurchasesDto findPurchaseById(Long id);
    PurchasesDto updatePurchase(PurchasesDto purchasesDto);
}
