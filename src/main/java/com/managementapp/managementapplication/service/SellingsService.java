package com.managementapp.managementapplication.service;

import com.managementapp.managementapplication.entity.SellingsEntity;
import com.managementapp.managementapplication.shared.dto.SellingsDto;
import com.managementapp.managementapplication.ui.response.OperationStatusModel;
import com.managementapp.managementapplication.ui.response.sellingsResponse.SellingsResponse;

public interface SellingsService {
    SellingsResponse findAll(int page, int limit, String sortBy, String sortDir);
    SellingsDto findById(Long id);
    SellingsDto createSell(SellingsDto sellingsDto);
    SellingsDto updateSell(SellingsDto sellingsDto);
    OperationStatusModel deleteSell(Long id , SellingsDto sellingsDto);
}
