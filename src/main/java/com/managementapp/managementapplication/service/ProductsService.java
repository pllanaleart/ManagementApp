package com.managementapp.managementapplication.service;

import com.managementapp.managementapplication.shared.dto.ProductsDto;
import com.managementapp.managementapplication.ui.response.OperationStatusModel;
import com.managementapp.managementapplication.ui.response.productResponse.ProductResponseList;

public interface ProductsService {

    ProductResponseList getAll(int page, int limitperpage,String sortBy,String sortDir);
    ProductsDto findById(Long id);
    ProductsDto createProduct(ProductsDto productsDto);
    ProductsDto updateProduct(Long id, ProductsDto productsDto);
    OperationStatusModel deleteProduct(Long id);
}
