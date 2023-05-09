package com.managementapp.managementapplication.service;

import com.managementapp.managementapplication.entity.ProductsListEntity;
import com.managementapp.managementapplication.shared.dto.InvoiceDto;
import com.managementapp.managementapplication.shared.dto.ProductsListDto;

public interface InvoiceService {

    InvoiceDto createInvoice(InvoiceDto invoiceDto);
    ProductsListDto createProductsList(ProductsListDto productsListDto);
    InvoiceDto updateInvoice(InvoiceDto invoiceDto);
}
