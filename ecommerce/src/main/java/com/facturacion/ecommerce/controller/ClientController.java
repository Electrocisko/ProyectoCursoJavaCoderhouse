package com.facturacion.ecommerce.controller;

import com.facturacion.ecommerce.exception.ClientAlreadyRegisteredException;
import com.facturacion.ecommerce.exception.ClientNotFoundException;
import com.facturacion.ecommerce.persistence.model.ClientModel;
import com.facturacion.ecommerce.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/client")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @PostMapping(path = "/create")
    public ResponseEntity<ClientModel> create(@RequestBody ClientModel newClient) throws ClientAlreadyRegisteredException {
        return new ResponseEntity<>(this.clientService.create(newClient), HttpStatus.OK );
    }

    @GetMapping(path = "/all")
    public ResponseEntity<List<ClientModel>> findAll() {
        return new ResponseEntity<>(this.clientService.findAll(), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ClientModel> findById(@PathVariable Integer id) throws Exception {
        return new ResponseEntity<>(this.clientService.findById(id), HttpStatus.OK);
    }

    @PutMapping(path = "/update/{id}")
    public ResponseEntity<ClientModel> update(@RequestBody ClientModel clientUpdated,@PathVariable Integer id) throws Exception {
        return new ResponseEntity<>(this.clientService.update(clientUpdated,id),HttpStatus.OK);
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Integer id) throws Exception {
        return new ResponseEntity<>(this.clientService.deleteById(id), HttpStatus.OK) ;
    }
}

