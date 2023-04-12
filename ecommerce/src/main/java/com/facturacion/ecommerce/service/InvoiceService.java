package com.facturacion.ecommerce.service;

import com.facturacion.ecommerce.dto.DetailsDTO;
import com.facturacion.ecommerce.dto.InvoiceDTO;
import com.facturacion.ecommerce.exception.InvoiceNotFoundException;
import com.facturacion.ecommerce.persistence.model.ClientModel;
import com.facturacion.ecommerce.persistence.model.InvoiceDetailsModel;
import com.facturacion.ecommerce.persistence.model.InvoiceModel;
import com.facturacion.ecommerce.persistence.model.ProductModel;
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
    private ClientService clientService;
    @Autowired
    private InvoiceDetailsService invoiceDetailsService;
    @Autowired
    private ProductService productService;

    public InvoiceModel create(InvoiceModel newData) throws Exception {
        InvoiceModel newInvoice = new InvoiceModel();
        newInvoice.setCreated(LocalDate.now());
        //Obtengo Id del cliente
        Integer clientId = newData.getClient_id().getId();
       //Busco al cliente
        ClientModel clientToAdd = clientService.findById(clientId); //VALIDACIONES DE CLIENTE???
        newInvoice.setClient_id(clientToAdd);

        // Obtengo la invoiceDetails que tendria que adjuntar al invoice.
        List<InvoiceDetailsModel> invoiceDetails = newData.getInvoiceDetails();
        // Ahora tendria que hacer un recorrido para hacer validaciones de cada producto.

        //Creo un invoiceSaved antes de retornar para obtener el id asignado al invoice recien creado
        //ANTES HACER TODAS LAS VALIDACIONES!!!!!
        InvoiceModel invoiceSaved = this.invoiceRepository.save(newInvoice);
        List<InvoiceDetailsModel> detailsToAdd = new ArrayList<>();

        for (InvoiceDetailsModel invoiceDetail: invoiceDetails
             ) {
            ProductModel productToAdd = productService.findById(invoiceDetail.getProductModel().getId());
            // Voy creando un nuevo detail y le agrego los datos que necesito
            InvoiceDetailsModel newDetail = new InvoiceDetailsModel();
            newDetail.setProductModel(productToAdd);
            newDetail.setAmount(invoiceDetail.getAmount());
            newDetail.setInvoiceModel(invoiceSaved);
            // Creo la lista y voy agregando cada detail fuera del ciclo agrego la lista al invoice
            InvoiceDetailsModel newDetailToAdd = this.invoiceDetailsService.create(newDetail);
            detailsToAdd.add(newDetailToAdd);
        }
        // Ahora tengo que actualizar el total del invoice segun los details nuevos.
        newInvoice.setInvoiceDetails(detailsToAdd);

        double totalPrice = 0;

        for (InvoiceDetailsModel item: detailsToAdd
             ) {
            System.out.println("Cantidad" + item.getAmount() + " Producto: " + item.getProductModel().getDescription() +
                    " Precio " + item.getProductModel().getPrice());
            totalPrice = totalPrice + (item.getAmount() * item.getProductModel().getPrice());
        }
       newInvoice.setTotal(totalPrice);

        invoiceSaved = this.invoiceRepository.save(newInvoice);
           return invoiceSaved;
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
        //Creo un InvoiceDTO para cargar los datos que quiero que se muestren
        InvoiceDTO invoiceDTO = new InvoiceDTO();
        invoiceDTO.setId(invoice.getId());
        invoiceDTO.setClient_id(invoice.getClient_id().getId());
        invoiceDTO.setClientName(invoice.getClient_id().getName() + " " + invoice.getClient_id().getLastname());
        invoiceDTO.setTotal(invoice.getTotal());
        // Creo una lista del tipo detailsDTO, para incluirlo despues InvoiceDTO
        List<DetailsDTO> products = new ArrayList<>();
        // Recorro el invoices details original, para crear nuevos detailsDTO e incluirlos en el listado
        for (InvoiceDetailsModel item : invoice.getInvoiceDetails()) {
            DetailsDTO detailsDTO = new DetailsDTO();
            detailsDTO.setProduct(item.getProductModel().getDescription());
            detailsDTO.setAmount(item.getAmount());
            detailsDTO.setPrice(item.getProductModel().getPrice());
            detailsDTO.setSubTotal(item.getSubTotal());
            // Voy agregando cada detalle
            products.add(detailsDTO);
        }
        //Agrego la lista de productos ya depurados de lo que quiero mostras
        invoiceDTO.setProducts(products);
        return invoiceDTO;
    }

}


