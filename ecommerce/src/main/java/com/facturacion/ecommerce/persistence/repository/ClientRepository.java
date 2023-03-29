package com.facturacion.ecommerce.persistence.repository;

import com.facturacion.ecommerce.persistence.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Integer> {
}
