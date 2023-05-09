package com.managementapp.managementapplication.entity;

import com.managementapp.managementapplication.embededKeys.ProductsListKey;
import jakarta.persistence.*;

@Entity(name = "products_list")
public class ProductsListEntity {
    @EmbeddedId
    private ProductsListKey id;
    @ManyToOne
    @MapsId("inoviceId")
    @JoinColumn(name = "invoice_id")
    private InvoiceEntity invoiceEntity;
    @ManyToOne
    @MapsId("productsId")
    @JoinColumn(name = "product_id")
    private ProductsEntity products;
    private int quantity;

    public ProductsListKey getId() {
        return id;
    }

    public void setId(ProductsListKey id) {
        this.id = id;
    }

    public InvoiceEntity getInvoiceEntity() {
        return invoiceEntity;
    }

    public void setInvoiceEntity(InvoiceEntity invoiceEntity) {
        this.invoiceEntity = invoiceEntity;
    }

    public ProductsEntity getProducts() {
        return products;
    }

    public void setProducts(ProductsEntity products) {
        this.products = products;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
