package com.facturacion.ecommerce.persistence.repository;

import com.facturacion.ecommerce.persistence.model.InvoiceDetailsModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceDetailsRepository extends JpaRepository<InvoiceDetailsModel, Integer> {
}
