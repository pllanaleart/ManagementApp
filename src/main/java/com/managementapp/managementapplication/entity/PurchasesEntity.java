package com.managementapp.managementapplication.entity;

import jakarta.persistence.*;

import java.util.Date;
import java.util.Set;

@Entity(name = "purchases")
public class PurchasesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    @OneToMany(mappedBy = "purchases")
    private Set<PurchaseProductEntity> products;
    private Date date;
    private Long buyInvoiceNumber;
    @OneToOne(mappedBy = "purchasesId")
    private ExpensesEntity expensesEntity;

    public ExpensesEntity getExpensesEntity() {
        return expensesEntity;
    }

    public void setExpensesEntity(ExpensesEntity expensesEntity) {
        this.expensesEntity = expensesEntity;
    }

    public PurchasesEntity() {
        date= new Date();
    }

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

    public Set<PurchaseProductEntity> getProducts() {
        return products;
    }

    public void setProducts(Set<PurchaseProductEntity> products) {
        this.products = products;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getBuyInvoiceNumber() {
        return buyInvoiceNumber;
    }

    public void setBuyInvoiceNumber(Long buyInvoiceNumber) {
        this.buyInvoiceNumber = buyInvoiceNumber;
    }
}
