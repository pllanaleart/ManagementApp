package com.managementapp.managementapplication.ui.response.productResponse;

public class PoductQuantityModel {

    private String name;
    private double price;
    private int quantity;
    private double TVSH;
    private double productTotal;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTVSH() {
        return TVSH;
    }

    public void setTVSH(double TVSH) {
        this.TVSH = TVSH;
    }

    public double getProductTotal() {
        return productTotal;
    }

    public void setProductTotal(double productTotal) {
        this.productTotal = productTotal;
    }
}
