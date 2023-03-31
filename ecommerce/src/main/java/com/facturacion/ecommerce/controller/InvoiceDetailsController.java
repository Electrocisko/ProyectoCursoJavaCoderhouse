package com.facturacion.ecommerce.controller;

import com.facturacion.ecommerce.persistence.model.InvoiceDetailsModel;
import com.facturacion.ecommerce.persistence.model.InvoiceModel;
import com.facturacion.ecommerce.service.InvoiceDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/details")
public class InvoiceDetailsController {

    @Autowired
    private InvoiceDetailsService invoiceDetailsService;

    @PostMapping(path = "/create")
    public ResponseEntity<InvoiceDetailsModel> create(@RequestBody InvoiceDetailsModel newDetails) {
        return new ResponseEntity<>(this.invoiceDetailsService.create(newDetails), HttpStatus.CREATED );
    }

    @GetMapping(path = "/all")
    public ResponseEntity<List<InvoiceDetailsModel>> findAll() {
        return  new ResponseEntity<>(this.invoiceDetailsService.findAll(), HttpStatus.OK);
    }

    @GetMapping(path = "/get/{id}")
    public ResponseEntity<InvoiceDetailsModel> findById(@PathVariable Integer id) {
        return new ResponseEntity<>(this.invoiceDetailsService.findById(id), HttpStatus.OK);
    }

    @PutMapping(path = "/updated/{id}")
    public ResponseEntity<InvoiceDetailsModel> updated(@RequestBody InvoiceDetailsModel newData, @PathVariable Integer id){
        return new ResponseEntity<>(this.invoiceDetailsService.updated(newData,id), HttpStatus.OK);
    }


}
