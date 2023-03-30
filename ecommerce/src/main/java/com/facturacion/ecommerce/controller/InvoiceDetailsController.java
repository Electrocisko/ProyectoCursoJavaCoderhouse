package com.facturacion.ecommerce.controller;

import com.facturacion.ecommerce.persistence.model.InvoiceDetailsModel;
import com.facturacion.ecommerce.persistence.model.InvoiceModel;
import com.facturacion.ecommerce.service.InvoiceDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/details")
public class InvoiceDetailsController {

    @Autowired
    private InvoiceDetailsService invoiceDetailsService;

    @PostMapping(path = "/create")
    public ResponseEntity<InvoiceDetailsModel> create(@RequestBody InvoiceDetailsModel newDetails) {
        return new ResponseEntity<>(this.invoiceDetailsService.create(newDetails), HttpStatus.CREATED );
    }

}
