package com.facturacion.ecommerce.service;

import com.facturacion.ecommerce.persistence.model.ProductModel;
import com.facturacion.ecommerce.persistence.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public ProductModel create(ProductModel newProduct) {
        return this.productRepository.save(newProduct);
    }


}
