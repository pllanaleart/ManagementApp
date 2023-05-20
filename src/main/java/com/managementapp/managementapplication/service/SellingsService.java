package com.managementapp.managementapplication.service;

import com.managementapp.managementapplication.shared.dto.SellingsDto;

public interface SellingsService {
    SellingsDto findById(Long id);
}
