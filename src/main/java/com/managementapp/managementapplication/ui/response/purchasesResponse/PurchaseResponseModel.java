package com.managementapp.managementapplication.ui.response.purchasesResponse;

import java.util.Date;
import java.util.Set;

public class PurchaseResponseModel {

    private Long id;
    private String description;
    private Set<PurchaseProductsResponseList> productsResponseLists;
    private Date date;
    private Long buyInvoiceId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<PurchaseProductsResponseList> getProductsResponseLists() {
        return productsResponseLists;
    }

    public void setProductsResponseLists(Set<PurchaseProductsResponseList> productsResponseLists) {
        this.productsResponseLists = productsResponseLists;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getBuyInvoiceId() {
        return buyInvoiceId;
    }

    public void setBuyInvoiceId(Long buyInvoiceId) {
        this.buyInvoiceId = buyInvoiceId;
    }
}
