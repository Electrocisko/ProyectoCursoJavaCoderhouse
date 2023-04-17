package com.facturacion.ecommerce.controller;

import com.facturacion.ecommerce.dto.InvoiceDTO;
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

    @PostMapping(path = "/createById")
    public ResponseEntity<InvoiceDTO> createById(@RequestBody InvoiceModel newInvoice) throws Exception{
        return new ResponseEntity<>(this.invoiceService.createById(newInvoice), HttpStatus.CREATED );
    }

    @PostMapping(path = "/createByCode")
    public ResponseEntity<InvoiceDTO> createByCode(@RequestBody InvoiceModel newInvoice) throws Exception {
        return new ResponseEntity<>(this.invoiceService.createByCode(newInvoice),HttpStatus.OK);
    }

    @GetMapping(path = "/all")
    public ResponseEntity<List<InvoiceDTO>> findAll() {
        return new ResponseEntity<>(this.invoiceService.findAll(),HttpStatus.OK);
    }

    @GetMapping(path = "/get/{id}")
        public ResponseEntity<InvoiceDTO> findById(@PathVariable Integer id)  throws Exception{
            return new ResponseEntity<>(this.invoiceService.findById(id),HttpStatus.OK);
    }

}
