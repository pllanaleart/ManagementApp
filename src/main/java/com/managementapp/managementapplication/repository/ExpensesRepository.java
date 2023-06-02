package com.managementapp.managementapplication.repository;

import com.managementapp.managementapplication.entity.ExpensesEntity;
import com.managementapp.managementapplication.entity.PurchasesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpensesRepository extends JpaRepository<ExpensesEntity,Long> {
    ExpensesEntity findByPurchasesId(PurchasesEntity purchases);
}
