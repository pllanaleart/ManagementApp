package com.managementapp.managementapplication.service;

import com.managementapp.managementapplication.entity.SellingsEntity;
import com.managementapp.managementapplication.shared.dto.SellingsDto;
import com.managementapp.managementapplication.ui.response.OperationStatusModel;

public interface SellingsService {
    SellingsDto findById(Long id);

    SellingsDto createSell(SellingsDto sellingsDto);
    SellingsDto updateSell(SellingsDto sellingsDto);
    OperationStatusModel deleteSell(Long id , SellingsDto sellingsDto);
}
