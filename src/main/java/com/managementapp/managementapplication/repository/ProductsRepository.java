package com.managementapp.managementapplication.repository;

import com.managementapp.managementapplication.entity.ProductsEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductsRepository extends PagingAndSortingRepository<ProductsEntity,Long> {
}
