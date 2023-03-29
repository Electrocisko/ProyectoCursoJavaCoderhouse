package com.facturacion.ecommerce.controller;

import com.facturacion.ecommerce.persistence.model.ClientModel;
import com.facturacion.ecommerce.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @PostMapping(path = "/create")
    public ResponseEntity<ClientModel> create(@RequestBody ClientModel newClient) {
        return new ResponseEntity<>(this.clientService.create(newClient), HttpStatus.OK );
    }

    @GetMapping(path = "/clients")
    public ResponseEntity  <List<ClientModel>> findAll() {
        return new ResponseEntity<>(this.clientService.findAll(), HttpStatus.OK);
    }


}
