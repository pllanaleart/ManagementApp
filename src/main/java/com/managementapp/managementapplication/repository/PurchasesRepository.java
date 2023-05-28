package com.managementapp.managementapplication.repository;

import com.managementapp.managementapplication.entity.PurchasesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchasesRepository extends JpaRepository<PurchasesEntity, Long> {
}
