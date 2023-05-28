package com.managementapp.managementapplication.shared.dto;


import java.util.Date;
import java.util.Set;

public class PurchasesDto {
    private Long id;
    private String description;
    private Set<PurchaseProductDto> products;
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

    public Set<PurchaseProductDto> getProducts() {
        return products;
    }

    public void setProducts(Set<PurchaseProductDto> products) {
        this.products = products;
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
