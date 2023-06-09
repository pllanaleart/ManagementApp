package com.managementapp.managementapplication.entity;

import jakarta.persistence.*;

import java.util.Set;

@Entity(name = "products")
public class ProductsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String mrp;
    private Long barcodeNumber;
    private double price;
    @OneToOne(mappedBy = "products")
    private StockEntity stock_id;
    @OneToMany(mappedBy = "products")
    private Set<ProductsListEntity> listEntities;
    @OneToMany(mappedBy = "products")
    private Set<PurchaseProductEntity> purchases;

    public Set<PurchaseProductEntity> getPurchases() {
        return purchases;
    }

    public void setPurchases(Set<PurchaseProductEntity> purchases) {
        this.purchases = purchases;
    }

    public Set<ProductsListEntity> getListEntities() {
        return listEntities;
    }

    public void setListEntities(Set<ProductsListEntity> listEntities) {
        this.listEntities = listEntities;
    }

    public StockEntity getStock_id() {
        return stock_id;
    }

    public void setStock_id(StockEntity stock_id) {
        this.stock_id = stock_id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMrp() {
        return mrp;
    }

    public void setMrp(String mrp) {
        this.mrp = mrp;
    }

    public Long getBarcodeNumber() {
        return barcodeNumber;
    }

    public void setBarcodeNumber(Long barcodeNumber) {
        this.barcodeNumber = barcodeNumber;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
