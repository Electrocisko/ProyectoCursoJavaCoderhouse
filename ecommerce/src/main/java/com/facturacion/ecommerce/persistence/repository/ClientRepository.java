package com.facturacion.ecommerce.persistence.repository;

import com.facturacion.ecommerce.persistence.model.ClientModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<ClientModel, Integer> {

    Optional<ClientModel> findByDoc(String doc);

    Optional<ClientModel> findByName(String name);
}
;