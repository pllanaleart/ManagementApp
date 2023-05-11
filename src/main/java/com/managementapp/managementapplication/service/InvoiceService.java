package com.managementapp.managementapplication.service;

import com.managementapp.managementapplication.shared.dto.InvoiceDto;
import com.managementapp.managementapplication.shared.dto.ProductsListDto;
import com.managementapp.managementapplication.ui.response.InvoiceResponseModel;

public interface InvoiceService {

    InvoiceDto createInvoice(InvoiceDto invoiceDto);
    ProductsListDto createProductsList(ProductsListDto productsListDto);
    InvoiceDto findByInvoiceId(Long id);


}
