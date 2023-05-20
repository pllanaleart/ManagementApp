package com.managementapp.managementapplication.repository;

import com.managementapp.managementapplication.entity.InvoiceEntity;
import com.managementapp.managementapplication.entity.SellingsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SellingsRepository extends JpaRepository<SellingsEntity, Long> {

    SellingsEntity findByInvoiceEntity(InvoiceEntity invoiceEntity);
}
