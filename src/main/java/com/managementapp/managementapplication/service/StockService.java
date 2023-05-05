package com.managementapp.managementapplication.service;

import com.managementapp.managementapplication.shared.dto.StockDto;
import com.managementapp.managementapplication.ui.response.StockResponseList;

public interface StockService {

    StockResponseList getAll(int page,int limiperpage, String sortBy, String sortDir);
    StockDto createStock(StockDto stockDto);
}
