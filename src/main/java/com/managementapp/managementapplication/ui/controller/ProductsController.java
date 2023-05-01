package com.managementapp.managementapplication.ui.controller;

import com.managementapp.managementapplication.service.ProductsService;
import com.managementapp.managementapplication.shared.dto.ProductsDto;
import org.springframework.stereotype.Repository;
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
    public List<ProductsDto> getAll(@RequestParam(value = "page",defaultValue = "0")
                                        int page,@RequestParam(value = "limit",defaultValue = "10")int limit){

        return new ArrayList<>(productsService.getAll(page, limit));
    }
    @GetMapping("/{id}")
    public ProductsDto findById(@PathVariable Long id){

        return productsService.findById(id);
    }
    @PostMapping
    public ProductsDto createProduct(@RequestBody ProductsDto productsDto){

        return  productsService.createProduct(productsDto);
    }
}
