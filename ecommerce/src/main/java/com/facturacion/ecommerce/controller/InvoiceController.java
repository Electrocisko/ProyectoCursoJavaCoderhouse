package com.facturacion.ecommerce.controller;

import com.facturacion.ecommerce.persistence.model.ClientModel;
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
    //Creo este endpoint para crear un invoice que va estar asociado a un cliente
    @PostMapping(path = "/createInvoice/{id}")
    public ResponseEntity<InvoiceModel> createInvoice(@PathVariable Integer id) {
        return new ResponseEntity<>(this.invoiceService.createInvoice(id), HttpStatus.CREATED );
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


    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Integer id) throws Exception {
        return new ResponseEntity<>(this.invoiceService.deleteById(id), HttpStatus.OK);
    }


}
