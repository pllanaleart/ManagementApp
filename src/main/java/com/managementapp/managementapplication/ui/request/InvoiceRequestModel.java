package com.managementapp.managementapplication.ui.request;

import java.util.List;

public class InvoiceRequestModel {
    private Long invoiceNo;
    private List<InvoiceQuantityModel> invoiceQuantityModels;


    public Long getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(Long invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public List<InvoiceQuantityModel> getInvoiceQuantityModels() {
        return invoiceQuantityModels;
    }

    public void setInvoiceQuantityModels(List<InvoiceQuantityModel> invoiceQuantityModels) {
        this.invoiceQuantityModels = invoiceQuantityModels;
    }
}
