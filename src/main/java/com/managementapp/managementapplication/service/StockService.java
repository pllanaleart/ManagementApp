package com.managementapp.managementapplication.service;

import com.managementapp.managementapplication.shared.dto.StockDto;
import com.managementapp.managementapplication.ui.response.OperationStatusModel;
import com.managementapp.managementapplication.ui.response.stockResponse.StockResponseList;

public interface StockService {

    StockResponseList getAll(int page,int limiperpage, String sortBy, String sortDir);
    StockDto createStock(StockDto stockDto);
    StockDto findByProductId(Long id);
    StockDto updateStock(StockDto stockDto);
    OperationStatusModel deleteStock(StockDto stockDto);
}
