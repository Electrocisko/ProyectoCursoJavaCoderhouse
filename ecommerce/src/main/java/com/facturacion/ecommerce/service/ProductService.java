package com.facturacion.ecommerce.service;

import com.facturacion.ecommerce.exception.ProductAlreadyExistsException;
import com.facturacion.ecommerce.exception.ProductNotFoundException;
import com.facturacion.ecommerce.persistence.model.ProductModel;
import com.facturacion.ecommerce.persistence.repository.ProductRepository;
import com.facturacion.ecommerce.validator.ProductValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public ProductModel create(ProductModel newProduct) throws ProductAlreadyExistsException, IllegalArgumentException {
        Optional<ProductModel> productOp = this.productRepository.findByCode(newProduct.getCode());
        this.isPresent(productOp);
        ProductValidator.validate(newProduct);
        return this.productRepository.save(newProduct);
    }

    public ProductModel findById(Integer id) throws Exception {
        this.checkId(id);
        Optional<ProductModel> productOp = this.productRepository.findById(id);
        this.isEmpty(productOp, String.valueOf(id));
        if (productOp.get().isActive() == false) {
            throw new IllegalArgumentException("Product no active in database ID=" + productOp.get().getId());
        }
        return productOp.get();
    }

    public ProductModel findByCode(String code) throws ProductNotFoundException{
        Optional<ProductModel> productOp = this.productRepository.findByCode(code);
        this.isEmpty(productOp, code);
        if (productOp.get().isActive() == false) {
            throw new IllegalArgumentException("Product no active in database Code=" + productOp.get().getCode());
        }
        return productOp.get();
    }

    public List<ProductModel> findAll() {
        List<ProductModel> productList = this.productRepository.findAll();
        productList.removeIf(item -> item.isActive()== false);

        return productList;
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
            productUpdated.setStock(newData.getStock());
            productUpdated.setPrice(newData.getPrice());
            return this.productRepository.save(productUpdated);
        }

    }

    public ProductModel updateByCode(ProductModel newData) throws Exception{
        Optional<ProductModel> productOp = this.productRepository.findByCode(newData.getCode());
        if (productOp.isEmpty()) {
            throw new ProductNotFoundException("The product you are trying to request does not exist");
        }
            ProductModel productUpdated = productOp.get();
            productUpdated.setStock(newData.getStock());
            productUpdated.setPrice(newData.getPrice());
            return this.productRepository.save(productUpdated);
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

    public void isActive(Optional productOp)  {
        System.out.println("Esta activo??????");
    }
}
