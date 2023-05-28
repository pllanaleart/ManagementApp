package com.managementapp.managementapplication.shared.dto;

public class PurchaseProductDto {
    private Long id;
    private PurchasesDto purchasesDto;
    private ProductsDto productsDto;
    private Long quantity;
    private double buyPrice;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PurchasesDto getPurchasesDto() {
        return purchasesDto;
    }

    public void setPurchasesDto(PurchasesDto purchasesDto) {
        this.purchasesDto = purchasesDto;
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

    public double getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(double buyPrice) {
        this.buyPrice = buyPrice;
    }
}
