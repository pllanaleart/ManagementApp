package com.managementapp.managementapplication.ui.request;

import java.util.Set;

public class PurchaseRequestModel {

    private String description;
    private Long buyInvoiceId;
    private Set<ProductPurchaseRequestModel> products;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getBuyInvoiceId() {
        return buyInvoiceId;
    }

    public void setBuyInvoiceId(Long buyInvoiceId) {
        this.buyInvoiceId = buyInvoiceId;
    }

    public Set<ProductPurchaseRequestModel> getProducts() {
        return products;
    }

    public void setProducts(Set<ProductPurchaseRequestModel> products) {
        this.products = products;
    }
}
