package com.managementapp.managementapplication.entity;

import jakarta.persistence.*;

@Entity(name = "purchase_product")
public class PurchaseProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "purchase_id")
    private PurchasesEntity purchases;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductsEntity products;
    private Long quantity;
    private double buyPrice;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PurchasesEntity getPurchases() {
        return purchases;
    }

    public void setPurchases(PurchasesEntity purchases) {
        this.purchases = purchases;
    }

    public ProductsEntity getProducts() {
        return products;
    }

    public void setProducts(ProductsEntity products) {
        this.products = products;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public double getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(double buyPrice) {
        this.buyPrice = buyPrice;
    }
}
