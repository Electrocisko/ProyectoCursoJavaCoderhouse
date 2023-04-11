package com.facturacion.ecommerce.service;

import com.facturacion.ecommerce.dto.DetailsDTO;
import com.facturacion.ecommerce.dto.InvoiceDTO;
import com.facturacion.ecommerce.exception.InvoiceNotFoundException;
import com.facturacion.ecommerce.persistence.model.ClientModel;
import com.facturacion.ecommerce.persistence.model.InvoiceDetailsModel;
import com.facturacion.ecommerce.persistence.model.InvoiceModel;
import com.facturacion.ecommerce.persistence.repository.ClientRepository;
import com.facturacion.ecommerce.persistence.repository.InvoiceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;
    @Autowired
    private ClientRepository clientRepository;

    public InvoiceModel create(InvoiceModel newInvoice) {
           return this.invoiceRepository.save(newInvoice);
    }

    public List<InvoiceModel> findAll(){
        return this.invoiceRepository.findAll();
    }

    public InvoiceDTO findById(Integer id) throws Exception{
        if(id <= 0) {
            throw new Exception("the id is not valid");
        }
        Optional<InvoiceModel> invoiceOp = this.invoiceRepository.findById(id);
        if (invoiceOp.isEmpty()) {
           throw new InvoiceNotFoundException("invoice not found with this id");
        }
        InvoiceModel invoice = invoiceOp.get();
        InvoiceDTO invoiceDTO = new InvoiceDTO();
        invoiceDTO.setId(invoice.getId());
        invoiceDTO.setClient_id(invoice.getClient_id().getId());
        invoiceDTO.setClientName(invoice.getClient_id().getName() + " " + invoice.getClient_id().getLastname());
        invoiceDTO.setTotal(invoice.getTotal());
        //////////////////////////////////////////////////////////////
        //DetailsDTO detailsDTO = new DetailsDTO();
        //List<InvoiceDetailsModel> productList = new ArrayList<>();



        List<String> products = new ArrayList<>();

        for (InvoiceDetailsModel item : invoice.getInvoiceDetails()) {
           // detailsDTO.setProduct(item.getProductModel().getDescription());
            //productList.add(item);
            products.add("Producto: " + item.getProductModel().getDescription() + " Precio c/u: $" +
                    item.getProductModel().getPrice() + " Cantidad: " +
                    item.getAmount() + " SubTotal: " +  item.getSubTotal());
        }
        //invoiceDTO.setProducts(productList);
        invoiceDTO.setProducts(products);

        /////////////////////////////////////////////////////////////
        return invoiceDTO;
    }

    public InvoiceModel update(InvoiceModel newData, Integer id) throws Exception {
        if(id <= 0) {
            throw new Exception("the id is not valid");
        }
        Optional<InvoiceModel> invoiceOp = this.invoiceRepository.findById(id);
        if (invoiceOp.isEmpty()) {
            throw new InvoiceNotFoundException("invoice not found with this id");
        }
        InvoiceModel invoiceUpdated = invoiceOp.get();
        invoiceUpdated.setTotal(newData.getTotal());
        return invoiceUpdated;
    }

    public String deleteById(Integer id) throws Exception {
        if(id <= 0) {
            throw new Exception("the id is not valid");
        }
        Optional<InvoiceModel> invoiceOp = this.invoiceRepository.findById(id);
        if (invoiceOp.isEmpty()) {
            throw new InvoiceNotFoundException("invoice not found with this id");
        }
        //Se dejo esto con fines didacticos nomas
        //this.invoiceRepository.deleteById(id);
        return "Invoice cannot be deleted. This is just a sample";
    }

    // Aca creo primero el invoice, con la fecha, el total en cero y el cliente.
    public InvoiceModel createInvoice( Integer client_id) {
        InvoiceModel newInvoice = new InvoiceModel();
        newInvoice.setCreated(LocalDate.now());
        newInvoice.setTotal(0);
        // Tendria que ver si es posible obtener el cliente con el id que me mandaron por parametro
     Optional<ClientModel> clientOp = this.clientRepository.findById(client_id);
        newInvoice.setClient_id(clientOp.get());
        return this.invoiceRepository.save(newInvoice);
    }
}


