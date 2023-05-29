package com.managementapp.managementapplication.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity(name = "expenses")
public class ExpensesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private double totalValue;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "purchasesId",unique = true,nullable = true,referencedColumnName = "id")
    private PurchasesEntity purchasesId;
    private Date date;

    public ExpensesEntity() {
        date=new Date();
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

    public double getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(double totalValue) {
        this.totalValue = totalValue;
    }

    public PurchasesEntity getPurchasesId() {
        return purchasesId;
    }

    public void setPurchasesId(PurchasesEntity purchasesId) {
        this.purchasesId = purchasesId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
