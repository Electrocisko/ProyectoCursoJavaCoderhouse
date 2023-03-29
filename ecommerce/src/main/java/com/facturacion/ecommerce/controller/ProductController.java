package com.facturacion.ecommerce.controller;

import com.facturacion.ecommerce.persistence.model.ProductModel;
import com.facturacion.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/product")
public class ProductController {

@Autowired
    private  ProductService productService;

@PostMapping(path = "/create")
    public ResponseEntity<ProductModel> create(@RequestBody ProductModel newProduct) throws Exception {
    return new ResponseEntity<>(this.productService.create(newProduct), HttpStatus.OK );
}




}
