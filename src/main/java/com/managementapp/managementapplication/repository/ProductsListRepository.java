package com.managementapp.managementapplication.repository;

import com.managementapp.managementapplication.embededKeys.ProductsListKey;
import com.managementapp.managementapplication.entity.InvoiceEntity;
import com.managementapp.managementapplication.entity.ProductsListEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ProductsListRepository extends JpaRepository<ProductsListEntity, ProductsListKey> {
    Set<ProductsListEntity> findAllByInvoiceEntity(InvoiceEntity invoiceEntity);
}
