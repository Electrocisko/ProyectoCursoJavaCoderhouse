package com.facturacion.ecommerce.persistence.repository;

import com.facturacion.ecommerce.persistence.model.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<ProductModel, Integer> {

    Optional<ProductModel>  findByCode(String code);
    Optional<ProductModel> findById(Integer id);
}
