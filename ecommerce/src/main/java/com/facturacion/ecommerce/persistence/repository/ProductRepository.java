package com.facturacion.ecommerce.persistence.repository;

import com.facturacion.ecommerce.persistence.model.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductModel, Integer> {
}
