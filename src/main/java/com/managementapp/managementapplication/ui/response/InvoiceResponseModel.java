package com.managementapp.managementapplication.ui.response;

import java.util.Set;

public class InvoiceResponseModel {

    private Long invoiceNo;
    private double ammount;
    private double tvsh;
    private double totalForPayment;
    private Set<PoductQuantityModel> products;

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

    public Set<PoductQuantityModel> getProducts() {
        return products;
    }

    public void setProducts(Set<PoductQuantityModel> products) {
        this.products = products;
    }


}
