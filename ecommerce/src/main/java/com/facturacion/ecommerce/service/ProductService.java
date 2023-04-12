package com.facturacion.ecommerce.service;

import com.facturacion.ecommerce.exception.ProductAlreadyExistsException;
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

    public ProductModel create(ProductModel newProduct) throws ProductAlreadyExistsException {
        Optional<ProductModel> productOp = this.productRepository.findByCode(newProduct.getCode());
        this.isPresent(productOp);
        return this.productRepository.save(newProduct);
    }

    public ProductModel findById(Integer id) throws Exception {
        this.checkId(id);
        Optional<ProductModel> productOp = this.productRepository.findById(id);
        this.isEmpty(productOp, String.valueOf(id));
        return productOp.get();
    }

    public ProductModel findByCode(String code) throws ProductNotFoundException{
        Optional<ProductModel> productOp = this.productRepository.findByCode(code);
        this.isEmpty(productOp, code);
        return productOp.get();
    }

    public List<ProductModel> findAll() {
        return this.productRepository.findAll();
    }

    public ProductModel update(ProductModel newData, Integer id) throws Exception{
        this.checkId(id);
    Optional<ProductModel> productOp = this.productRepository.findById(id);
        if (productOp.isEmpty()) {
            throw new ProductNotFoundException("The product you are trying to request does not exist");
        } else {
            if (!(newData.getCode().equals(productOp.get().getCode()))) {
                Optional<ProductModel> prodOP = this.productRepository.findByCode(newData.getCode());
                if (prodOP.isPresent()){
                    throw new ProductAlreadyExistsException("the new code already exists in the database");
                }
            }

            ProductModel productUpdated = productOp.get();
            productUpdated.setDescription(newData.getDescription());
            productUpdated.setCode(newData.getCode());
            productUpdated.setStock(newData.getStock());
            productUpdated.setPrice(newData.getPrice());
            return this.productRepository.save(productUpdated);
        }

    }

    public String deleteById(Integer id) throws Exception {
        this.checkId(id);
        Optional<ProductModel> productOp = this.productRepository.findById(id);
        if (productOp.isEmpty()){
            throw new ProductNotFoundException("The product you are trying to request does not exist");
        }
        //Cambio el estado de activo a false y actualizo el producto
        productOp.get().setActive(false);
        this.productRepository.save(productOp.get());
        return "Producto Eliminado";
    }

    public void checkId(Integer id) throws Exception {
        if (id <= 0){
            throw new Exception("the id is not valid");
        }
    }

    public void isPresent(Optional productOp) throws ProductAlreadyExistsException {
        if (productOp.isPresent()) {
            throw new ProductAlreadyExistsException("The product you are trying to add already exists in the database    ");
        }
    }

    public void isEmpty(Optional productOp, String data) throws ProductNotFoundException {
        if (productOp.isEmpty()){
            throw new ProductNotFoundException("The product you are trying to request does not exist  ID: " + data );
        }

    }
}
