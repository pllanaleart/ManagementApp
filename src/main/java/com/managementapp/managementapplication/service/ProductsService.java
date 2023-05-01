package com.managementapp.managementapplication.service;

import com.managementapp.managementapplication.shared.dto.ProductsDto;

import java.util.List;

public interface ProductsService {

    List<ProductsDto> getAll(int page, int limitperpage);
    ProductsDto findById(Long id);
    ProductsDto createProduct(ProductsDto productsDto);
}
