package com.managementapp.managementapplication.entity;

import jakarta.persistence.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "invoice")
public class InvoiceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long invoiceNo;
    private Date dateCreated= new Date();
    private double ammount;
    private double tvsh;
    private double totalForPayment;
    @OneToMany(mappedBy = "invoiceEntity")
    private Set<ProductsListEntity> listEntities;

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

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Set<ProductsListEntity> getListEntities() {
        return listEntities;
    }

    public void setListEntities(Set<ProductsListEntity> listEntities) {
        this.listEntities = listEntities;
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
}
