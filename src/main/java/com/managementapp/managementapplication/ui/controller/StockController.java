package com.managementapp.managementapplication.ui.controller;


import com.managementapp.managementapplication.service.StockService;
import com.managementapp.managementapplication.shared.AppConstants;
import com.managementapp.managementapplication.shared.dto.StockDto;
import com.managementapp.managementapplication.ui.request.StockUpdateRequestModel;
import com.managementapp.managementapplication.ui.response.OperationStatusModel;
import com.managementapp.managementapplication.ui.response.StockResponseList;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("stock")
public class StockController {

    private StockService stockService;
    ModelMapper mapper=new ModelMapper();

    @Autowired
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
    @PostMapping
    public StockDto createStock(@RequestBody StockDto stockDto){
        return stockService.createStock(stockDto);
    }

    @GetMapping("/{id}")
    public StockDto findByProductId(@PathVariable Long id){
        return stockService.findByProductId(id);
    }
    @PutMapping("/{id}")
    public StockDto updateStock(@RequestBody StockUpdateRequestModel updateRequestModel, @PathVariable Long id){
        StockDto stockDto = stockService.findByProductId(id);
        if(stockDto == null)throw new RuntimeException("Product not in stock");
        stockDto.setType(updateRequestModel.getType());
        stockDto.setUnit(updateRequestModel.getUnit());
        return stockService.updateStock(stockDto);
    }

    @DeleteMapping("/{id}")
    public OperationStatusModel deleteStock(@PathVariable Long id){
        StockDto stockDto =stockService.findByProductId(id);
        if(stockDto == null)throw new RuntimeException("Product not in stock");
        return stockService.deleteStock(stockDto);
    }
}
