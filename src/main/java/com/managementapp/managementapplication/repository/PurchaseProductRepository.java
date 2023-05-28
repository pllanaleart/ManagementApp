package com.managementapp.managementapplication.repository;

import com.managementapp.managementapplication.entity.PurchaseProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseProductRepository extends JpaRepository<PurchaseProductEntity, Long> {
}
