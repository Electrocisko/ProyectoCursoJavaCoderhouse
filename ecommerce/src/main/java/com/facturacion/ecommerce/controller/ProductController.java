package com.facturacion.ecommerce.controller;

import com.facturacion.ecommerce.exception.ProductAlreadyExistsException;
import com.facturacion.ecommerce.exception.ProductNotFoundException;
import com.facturacion.ecommerce.persistence.model.ProductModel;
import com.facturacion.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/product")
public class ProductController {

@Autowired
    private  ProductService productService;

@PostMapping(path = "/create")
    public ResponseEntity<ProductModel> create(@RequestBody ProductModel newProduct) throws ProductAlreadyExistsException {
    return new ResponseEntity<>(this.productService.create(newProduct), HttpStatus.OK );
}

@GetMapping(path = "/getById/{id}")
    public ResponseEntity<ProductModel> findById(@PathVariable Integer id) throws Exception {
    return new ResponseEntity<>(this.productService.findById(id),HttpStatus.OK );
}

@GetMapping(path = "/getByCode/{code}")
public ResponseEntity<ProductModel> findByCode(@PathVariable String code) throws ProductNotFoundException {
    return new ResponseEntity<>(this.productService.findByCode(code), HttpStatus.NOT_FOUND);
}

@GetMapping(path = "/all")
    public ResponseEntity<List<ProductModel>> findAll() {
    return new ResponseEntity<>(this.productService.findAll(), HttpStatus.OK);
}

@PutMapping(path = "/update/{id}")
    public ResponseEntity<ProductModel> update(@RequestBody ProductModel productUpdate, @PathVariable Integer id) throws Exception {
    return new ResponseEntity<>(this.productService.update(productUpdate, id), HttpStatus.OK);
}

@DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Integer id) throws Exception {
    return new ResponseEntity<>(this.productService.deleteById(id),HttpStatus.OK);
}


}


