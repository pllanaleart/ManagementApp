package com.managementapp.managementapplication.repository;

import com.managementapp.managementapplication.entity.ExpensesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpensesRepository extends JpaRepository<ExpensesEntity,Long> {
}
