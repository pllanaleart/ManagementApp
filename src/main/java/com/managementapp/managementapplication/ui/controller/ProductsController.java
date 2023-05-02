package com.managementapp.managementapplication.ui.controller;

import com.managementapp.managementapplication.service.ProductsService;
import com.managementapp.managementapplication.shared.AppConstants;
import com.managementapp.managementapplication.shared.dto.ProductsDto;
import com.managementapp.managementapplication.ui.response.OperationStatusModel;
import com.managementapp.managementapplication.ui.response.ProductResponseList;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("products")
public class ProductsController {
    ProductsService productsService;

    public ProductsController(ProductsService productsService) {
        this.productsService = productsService;
    }

    @GetMapping
    public ProductResponseList getAll(@RequestParam(value = "page",defaultValue = AppConstants.DEFAULT_PAGE_NO) int page,
                                      @RequestParam(value = "limit",defaultValue = AppConstants.DEFAULT_PAGE_SIZE)int limit,
                                      @RequestParam(value = "sortBy",defaultValue = AppConstants.DEFAULT_SORT_BY) String sortBy,
                                      @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIR) String sortDir){

        return productsService.getAll(page, limit, sortBy,sortDir);
    }
    @GetMapping("/{id}")
    public ProductsDto findById(@PathVariable Long id){

        return productsService.findById(id);
    }
    @PostMapping
    public ProductsDto createProduct(@RequestBody ProductsDto productsDto){

        return  productsService.createProduct(productsDto);
    }
    @PutMapping("/{id}")
    public ProductsDto updateProduct(@PathVariable Long id,@RequestBody ProductsDto productsDto){

        return productsService.updateProduct(id,productsDto);
    }
    @DeleteMapping("/{id}")
    public OperationStatusModel deleteProduct(@PathVariable Long id){

        return productsService.deleteProduct(id);
    }
}
