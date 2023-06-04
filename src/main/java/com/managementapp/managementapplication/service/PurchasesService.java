package com.managementapp.managementapplication.service;

import com.managementapp.managementapplication.shared.dto.PurchasesDto;
import com.managementapp.managementapplication.ui.response.OperationStatusModel;
import com.managementapp.managementapplication.ui.response.purchasesResponse.PurchasesPagedResponseModel;

public interface PurchasesService {

    PurchasesPagedResponseModel getAll(int page, int limit, String sortBy, String sortDir);
    PurchasesDto createPurchase(PurchasesDto purchasesDto);
    PurchasesDto findPurchaseById(Long id);
    PurchasesDto updatePurchase(PurchasesDto purchasesDto);
}
