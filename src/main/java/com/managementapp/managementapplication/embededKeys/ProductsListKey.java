package com.managementapp.managementapplication.embededKeys;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ProductsListKey implements Serializable {
    @Column(name = "invoice_id")
    private Long invoice_id;
    @Column(name = "product_id")
    private Long product_id;

    public ProductsListKey() {
    }

    public ProductsListKey(Long invoice_id, Long product_id) {
        this.invoice_id = invoice_id;
        this.product_id = product_id;
    }

    public Long getInvoice_id() {
        return invoice_id;
    }

    public void setInvoice_id(Long invoice_id) {
        this.invoice_id = invoice_id;
    }

    public Long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Long product_id) {
        this.product_id = product_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductsListKey that = (ProductsListKey) o;
        return Objects.equals(invoice_id, that.invoice_id) && Objects.equals(product_id, that.product_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(invoice_id, product_id);
    }
}
