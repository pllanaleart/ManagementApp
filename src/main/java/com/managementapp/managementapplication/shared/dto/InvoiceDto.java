package com.managementapp.managementapplication.shared.dto;

import com.managementapp.managementapplication.entity.ProductsListEntity;

import java.util.Date;
import java.util.Set;

public class InvoiceDto {

    private Long id;
    private Long invoiceNo;
    private double ammount;
    private double tvsh;
    private double totalForPayment;
    private Date dateCreated;
    private Set<ProductsListDto> productsListDtos;
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

    public Set<ProductsListDto> getProductsListDtos() {
        return productsListDtos;
    }

    public void setProductsListDtos(Set<ProductsListDto> productsListDtos) {
        this.productsListDtos = productsListDtos;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }
}
