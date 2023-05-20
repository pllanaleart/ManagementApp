package com.managementapp.managementapplication.service;

import com.managementapp.managementapplication.embededKeys.ProductsListKey;
import com.managementapp.managementapplication.shared.dto.InvoiceDto;
import com.managementapp.managementapplication.shared.dto.ProductsListDto;
import com.managementapp.managementapplication.ui.response.invoiceResponse.InvoiceTransferList;
import com.managementapp.managementapplication.ui.response.OperationStatusModel;

public interface InvoiceService {

    InvoiceTransferList findAll(int page, int limit, String sortBy, String sortDir);
    InvoiceDto createInvoice(InvoiceDto invoiceDto);
    ProductsListDto createProductsList(ProductsListDto productsListDto);
    InvoiceDto findByInvoiceId(Long id);
    InvoiceDto updateInvoice(InvoiceDto invoiceDto);
    OperationStatusModel deleteInvoice(InvoiceDto invoiceDto);

    ProductsListDto findById(ProductsListKey productsListKey);

}
