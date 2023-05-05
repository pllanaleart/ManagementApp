package com.managementapp.managementapplication.ui.controller;

import com.managementapp.managementapplication.service.ProductsService;
import com.managementapp.managementapplication.service.StockService;
import com.managementapp.managementapplication.shared.AppConstants;
import com.managementapp.managementapplication.shared.dto.StockDto;
import com.managementapp.managementapplication.ui.response.StockResponseList;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("stock")
public class StockController {

    private StockService stockService;
    private ProductsService productsService;
    ModelMapper mapper = new ModelMapper();
    @Autowired
    public StockController(StockService stockService, ProductsService productsService) {
        this.stockService = stockService;
        this.productsService = productsService;
    }

    @GetMapping
    public StockResponseList getAll(@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NO) int page,
                                    @RequestParam(value = "limit", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int limit,
                                    @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY) String sortBy,
                                    @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIR) String sortDir) {

        return stockService.getAll(page, limit, sortBy, sortDir);
    }
    @PostMapping
    public StockDto createStock(@RequestBody StockDto stockDto){
        return stockService.createStock(stockDto);
    }
}
