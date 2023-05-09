package com.managementapp.managementapplication.shared.dto;

import com.managementapp.managementapplication.entity.ProductsListEntity;

import java.util.Date;
import java.util.List;
import java.util.Set;

public class InvoiceDto {

    private Long id;
    private Long invoiceNo;
    private double ammount;
    private double tvsh;
    private double totalForPayment;
    private Set<ProductsListDto> productsListEntities;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(Long invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public double getAmmount() {
        return ammount;
    }

    public void setAmmount(double ammount) {
        this.ammount = ammount;
    }

    public double getTvsh() {
        return tvsh;
    }

    public void setTvsh(double tvsh) {
        this.tvsh = tvsh;
    }

    public double getTotalForPayment() {
        return totalForPayment;
    }

    public void setTotalForPayment(double totalForPayment) {
        this.totalForPayment = totalForPayment;
    }

    public Set<ProductsListDto> getProductsListEntities() {
        return productsListEntities;
    }

    public void setProductsListEntities(Set<ProductsListDto> productsListEntities) {
        this.productsListEntities = productsListEntities;
    }
}
