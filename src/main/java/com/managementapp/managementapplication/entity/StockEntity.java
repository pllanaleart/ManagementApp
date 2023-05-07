package com.managementapp.managementapplication.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity(name = "stock")
public class StockEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(unique = true ,name = "product_id",referencedColumnName = "id")
    private ProductsEntity products;
    private Long quantity;
    private String unit;
    private String type;
    private Date date=new Date();

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProductsEntity getProductsEntity() {
        return products;
    }

    public void setProductsEntity(ProductsEntity productsEntity) {
        this.products = productsEntity;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
