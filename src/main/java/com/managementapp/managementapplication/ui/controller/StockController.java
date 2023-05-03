package com.managementapp.managementapplication.ui.controller;

import com.managementapp.managementapplication.service.StockService;
import com.managementapp.managementapplication.shared.AppConstants;
import com.managementapp.managementapplication.ui.response.StockResponseList;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("stock")
public class StockController {

    private StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping
    public StockResponseList getAll(@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NO) int page,
                                    @RequestParam(value = "limit", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int limit,
                                    @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY) String sortBy,
                                    @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIR) String sortDir) {

        return stockService.getAll(page, limit, sortBy, sortDir);
    }
}
