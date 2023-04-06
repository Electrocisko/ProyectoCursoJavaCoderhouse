package com.facturacion.ecommerce.service;

import com.facturacion.ecommerce.exception.ClientAlreadyRegisteredException;
import com.facturacion.ecommerce.exception.ClientNotFoundException;
import com.facturacion.ecommerce.persistence.model.ClientModel;
import com.facturacion.ecommerce.persistence.repository.ClientRepository;
import com.facturacion.ecommerce.validator.ClientValidator;
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

    public ClientModel create(ClientModel newClient) throws ClientAlreadyRegisteredException  {
        ClientValidator clientValidator = new ClientValidator();
        clientValidator.validate(newClient);
        Optional<ClientModel> clientOp = this.clientRepository.findByDoc(newClient.getDoc());
        this.ClientIsPresent(clientOp,"The client is already registered");
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
        this.ClientIsEmpty(clientOp,"client not found with this id");
        return clientOp.get();
    }

    public ClientModel findByDocNumber(String doc) throws ClientNotFoundException {
        Optional<ClientModel> clientOp = this.clientRepository.findByDoc(doc);
        this.ClientIsEmpty(clientOp,"client not found with this document number");
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
            Optional<ClientModel> clientDoc = this.clientRepository.findByDoc(newData.getDoc());
            if(!clientOp.get().getDoc().equals(newData.getDoc())) {
                System.out.println("Los documentos  no coinciden");
                if (clientDoc.isPresent()){
                    throw  new ClientAlreadyRegisteredException("The client is already registered with this document");
                }
            }
            ClientModel clientUpdated = clientOp.get();
            clientUpdated.setName(newData.getName());
            clientUpdated.setLastname(newData.getLastname());
            clientUpdated.setDoc(newData.getDoc());
            return  this.clientRepository.save(clientUpdated);
        }
    }

    public String deleteById(Integer id) throws Exception{
        if (id <= 0){
            throw new Exception("the id is not valid");
        }
        Optional<ClientModel> clientOp = this.clientRepository.findById(id);
        if (clientOp.isEmpty()) {
            throw new ClientNotFoundException("client not found with this id");
        }
         this.clientRepository.deleteById(id);
         return "Cliente Eliminado";
    }


    public void ClientIsPresent(Optional clientOp, String message) throws ClientAlreadyRegisteredException {
        if (clientOp.isPresent()) {
            throw new ClientAlreadyRegisteredException(message);
        }
    }

    public void ClientIsEmpty(Optional clientOp, String message) throws ClientNotFoundException {
        if (clientOp.isEmpty()){
            throw new ClientNotFoundException(message);
        }
    }

}
