package com.managementapp.managementapplication.repository;

import com.managementapp.managementapplication.embededKeys.ProductsListKey;
import com.managementapp.managementapplication.entity.ProductsListEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductsListRepository extends JpaRepository<ProductsListEntity, ProductsListKey> {
}
