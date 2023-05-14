package com.managementapp.managementapplication.service;

import com.managementapp.managementapplication.embededKeys.ProductsListKey;
import com.managementapp.managementapplication.entity.ProductsListEntity;
import com.managementapp.managementapplication.shared.dto.InvoiceDto;
import com.managementapp.managementapplication.shared.dto.ProductsListDto;

import java.util.Optional;

public interface InvoiceService {

    InvoiceDto createInvoice(InvoiceDto invoiceDto);
    ProductsListDto createProductsList(ProductsListDto productsListDto);
    InvoiceDto findByInvoiceId(Long id);
    InvoiceDto updateInvoice(InvoiceDto invoiceDto);

    ProductsListDto findById(ProductsListKey productsListKey);

}
