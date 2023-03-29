package com.facturacion.ecommerce.service;

import com.facturacion.ecommerce.persistence.model.ClientModel;
import com.facturacion.ecommerce.persistence.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    public ClientModel create(ClientModel newClient) {
    return this.clientRepository.save(newClient);
    }

    public List<ClientModel> findAll() {
        return this.clientRepository.findAll();
    }

    public ClientModel findById(Integer id) {
        Optional<ClientModel> clientOp = this.clientRepository.findById(id);
        return clientOp.get();
    }

}
