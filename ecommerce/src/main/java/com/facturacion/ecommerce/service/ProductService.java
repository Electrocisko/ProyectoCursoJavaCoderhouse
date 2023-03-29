package com.facturacion.ecommerce.service;

import com.facturacion.ecommerce.exception.ProductNotFoundException;
import com.facturacion.ecommerce.persistence.model.ProductModel;
import com.facturacion.ecommerce.persistence.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public ProductModel create(ProductModel newProduct) {
        return this.productRepository.save(newProduct);
    }

    public ProductModel findById(Integer id) throws Exception {
        if (id <= 0){
            throw new Exception("the id is not valid");
        }
        Optional<ProductModel> productOp = this.productRepository.findById(id);
        if (productOp.isEmpty()){
            throw new ProductNotFoundException("The product you are trying to request does not exist");
        }else {
            return productOp.get();
        }
    }

    public List<ProductModel> findAll() {
        return this.productRepository.findAll();
    }

    public ProductModel update(ProductModel newData, Integer id) throws Exception{
        if (id <= 0){
            throw new Exception("the id is not valid");
        }
    Optional<ProductModel> productOp = this.productRepository.findById(id);
        if (productOp.isEmpty()) {
            throw new ProductNotFoundException("The product you are trying to request does not exist");
        } else {
            ProductModel productUpdated = productOp.get();
            productUpdated.setDescription(newData.getDescription());
            productUpdated.setCode(newData.getCode());
            productUpdated.setStock(newData.getStock());
            productUpdated.setPrice(newData.getPrice());
            return this.productRepository.save(productUpdated);
        }

    }


}
