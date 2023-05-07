package com.managementapp.managementapplication.shared.dto;

import com.managementapp.managementapplication.entity.ProductsEntity;
import java.util.Date;

public class StockDto {
    private Long id;
    private ProductsDto productsDto;
    private Long quantity;
    private String unit;
    private String type;
    private Date date;

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

    public ProductsDto getProductsDto() {
        return productsDto;
    }

    public void setProductsDto(ProductsDto productsDto) {
        this.productsDto = productsDto;
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
