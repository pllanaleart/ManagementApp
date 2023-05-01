package com.managementapp.managementapplication.service;

import com.managementapp.managementapplication.shared.dto.ProductsDto;
import com.managementapp.managementapplication.ui.response.OperationStatusModel;
import com.managementapp.managementapplication.ui.response.ProductResponseList;

import java.util.List;

public interface ProductsService {

    ProductResponseList getAll(int page, int limitperpage);
    ProductsDto findById(Long id);
    ProductsDto createProduct(ProductsDto productsDto);
    ProductsDto updateProduct(Long id, ProductsDto productsDto);
    OperationStatusModel deleteProduct(Long id);
}
