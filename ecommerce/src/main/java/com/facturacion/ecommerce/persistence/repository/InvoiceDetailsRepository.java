package com.facturacion.ecommerce.persistence.repository;

import com.facturacion.ecommerce.persistence.model.InvoiceDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceDetailsRepository extends JpaRepository<InvoiceDetails, Integer> {
}
