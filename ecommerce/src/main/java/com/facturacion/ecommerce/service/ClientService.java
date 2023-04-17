package com.facturacion.ecommerce.service;

import com.facturacion.ecommerce.dto.ClientDTO;
import com.facturacion.ecommerce.exception.ClientAlreadyRegisteredException;
import com.facturacion.ecommerce.exception.ClientNotFoundException;
import com.facturacion.ecommerce.persistence.model.ClientModel;
import com.facturacion.ecommerce.persistence.model.InvoiceModel;
import com.facturacion.ecommerce.persistence.repository.ClientRepository;
import com.facturacion.ecommerce.validator.ClientValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public List<ClientDTO> findAll() {
        List<ClientModel> lista = this.clientRepository.findAll();
        //Depuro la lista sacando los que no estan activos
        lista.removeIf(item -> item.getActive()== false);
        List<ClientDTO> listDTO =  this.returnListDTO(lista);
        return listDTO;
    }

    public ClientModel findById(Integer id) throws Exception  {
        this.CheckId(id);
        Optional<ClientModel> clientOp = this.clientRepository.findById(id);
        this.ClientIsEmpty(clientOp,"client not found with this id");
        CheckStatus(clientOp.get());
        return clientOp.get();
    }

    public ClientDTO findByDocNumber(String doc) throws ClientNotFoundException {
        Optional<ClientModel> clientOp = this.clientRepository.findByDoc(doc);
        this.ClientIsEmpty(clientOp,"client not found with this document number");
        return this.returnClientDTO(clientOp.get());
    }

    public ClientModel update(ClientModel newData, Integer id) throws Exception {
        this.CheckId(id);
        Optional<ClientModel> clientOp = this.clientRepository.findById(id);
        this.ClientIsEmpty(clientOp,"client not found with this id");
            Optional<ClientModel> clientDoc = this.clientRepository.findByDoc(newData.getDoc());
            if(!clientOp.get().getDoc().equals(newData.getDoc())) {
                this.ClientIsPresent(clientOp,"The client is already registered whit this doc");
          }
            ClientModel clientUpdated = clientOp.get();
            clientUpdated.setName(newData.getName());
            clientUpdated.setLastname(newData.getLastname());
            clientUpdated.setDoc(newData.getDoc());
            return  this.clientRepository.save(clientUpdated);
        }
    //}

    public String deleteById(Integer id) throws Exception{
        this.CheckId(id);
        Optional<ClientModel> clientOp = this.clientRepository.findById(id);
        this.ClientIsEmpty(clientOp,"client not found with this id");
        clientOp.get().setActive(false);
        this.clientRepository.save(clientOp.get());
         return "client deleting logically";
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

    public void CheckId(Integer id) throws Exception{
        if (id <= 0){
            throw new Exception("the client id is not valid");
        }
    }

    public void CheckStatus(ClientModel client) throws ClientNotFoundException {
        if(!client.getActive()){
            throw new ClientNotFoundException("unsubscribed client");
        }
    }

    public List<ClientDTO> returnListDTO(List<ClientModel> list) {
        List<ClientDTO> clientDTOList = new ArrayList<>();
        for (ClientModel item: list
             ) {
        clientDTOList.add(this.returnClientDTO(item));
        }
        return clientDTOList;
    }

    public ClientDTO returnClientDTO(ClientModel item) {
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setId(item.getId());
        clientDTO.setCompleteName(item.getName() + " " + item.getLastname());
        clientDTO.setDocument(item.getDoc());
        //Aca tengo que hacer DTO de invoices
        List<InvoiceModel> invoicesList = item.getInvoiceModel();
        List<Integer> idInvoicesList = new ArrayList<>();
        for (InvoiceModel invoiceItem: invoicesList
        ) {
            idInvoicesList.add(invoiceItem.getId());
        }
        clientDTO.setInvoicesId(idInvoicesList);
        return  clientDTO;
    }


}

