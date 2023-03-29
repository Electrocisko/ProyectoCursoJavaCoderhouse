package com.facturacion.ecommerce.persistence.repository;

import com.facturacion.ecommerce.persistence.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {
}
