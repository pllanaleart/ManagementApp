package com.managementapp.managementapplication.repository;

import com.managementapp.managementapplication.entity.StockEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends JpaRepository<StockEntity,Long> {

    StockEntity findByProductsId(Long id);
}
