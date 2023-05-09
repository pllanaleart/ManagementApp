package com.managementapp.managementapplication.shared.dto;

import com.managementapp.managementapplication.embededKeys.ProductsListKey;

public class ProductsListDto {

    private ProductsListKey id;
    private int quantity;

    public ProductsListDto() {
    }

    public ProductsListDto(ProductsListKey id, int quantity) {
        this.id = id;
        this.quantity = quantity;
    }

    public ProductsListKey getId() {
        return id;
    }

    public void setId(ProductsListKey id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
