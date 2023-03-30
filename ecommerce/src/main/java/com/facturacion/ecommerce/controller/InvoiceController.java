package com.facturacion.ecommerce.controller;

import com.facturacion.ecommerce.persistence.model.InvoiceModel;
import com.facturacion.ecommerce.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/invoice")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @PostMapping(path = "/create")
    public ResponseEntity<InvoiceModel> create(@RequestBody InvoiceModel newInvoice) {
        return new ResponseEntity<>(this.invoiceService.create(newInvoice), HttpStatus.CREATED );
    }

    @GetMapping(path = "/all")
    public ResponseEntity<List<InvoiceModel>> findAll() {
        return new ResponseEntity<>(this.invoiceService.findAll(),HttpStatus.OK);
    }

    @GetMapping(path = "/get/{id}")
        public ResponseEntity<InvoiceModel> findById(@PathVariable Integer id)  throws Exception{
            return new ResponseEntity<>(this.invoiceService.findById(id),HttpStatus.OK);
    }

    @PutMapping(path = "/update/{id}")
    public ResponseEntity<InvoiceModel> updated(@RequestBody InvoiceModel newData,@PathVariable Integer id) throws Exception {
        return  new ResponseEntity<>(this.invoiceService.update(newData,id), HttpStatus.OK);
    }

}
