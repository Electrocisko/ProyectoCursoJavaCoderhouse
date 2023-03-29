package com.facturacion.ecommerce.service;

import com.facturacion.ecommerce.exception.ClientNotFoundException;
import com.facturacion.ecommerce.persistence.model.ClientModel;
import com.facturacion.ecommerce.persistence.repository.ClientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
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

    public ClientModel findById(Integer id) throws Exception  {
        if(id <= 0) {
            throw new Exception("the id is not valid");
        }
        Optional<ClientModel> clientOp = this.clientRepository.findById(id);

        if (clientOp.isEmpty()){
            throw new ClientNotFoundException("client not found with this id");
        }
        return clientOp.get();
    }

    public ClientModel update(ClientModel newData, Integer id) throws Exception {
        if (id <= 0){
            throw new Exception("the id is not valid");
        }
        Optional<ClientModel> clientOp = this.clientRepository.findById(id);
        if (clientOp.isEmpty()){
            throw new ClientNotFoundException("client not found with this id");
        } else {
            log.info("entra aqui?");
            ClientModel clientUpdated = clientOp.get();
            clientUpdated.setName(newData.getName());
            clientUpdated.setLastname(newData.getLastname());
            clientUpdated.setDocnumber(newData.getDocnumber());
            return  this.clientRepository.save(clientUpdated);
        }
    }

}
