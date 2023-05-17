package com.managementapp.managementapplication.repository;

import com.managementapp.managementapplication.entity.InvoiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InvoiceRepository extends JpaRepository<InvoiceEntity,Long> {

    InvoiceEntity findInvoiceEntityById(Long id);
}
