package com.facturacion.ecommerce.service;

import com.facturacion.ecommerce.dto.DetailsDTO;
import com.facturacion.ecommerce.dto.InvoiceDTO;
import com.facturacion.ecommerce.exception.InsufficientStockException;
import com.facturacion.ecommerce.exception.InvoiceDetailsNotFoundException;
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
import java.util.*;

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
        ClientModel clientToAdd = clientService.findById(clientId);
        newInvoice.setClient_id(clientToAdd);
        // Tengo que validar todo lo que me llega de la lista de invoice details newdata.
        this.validateInvoicesDetails(newData.getInvoiceDetails());


        //Creo un invoiceSaved antes de retornar para obtener el id asignado al invoice recien creado
        InvoiceModel invoiceSaved = this.invoiceRepository.save(newInvoice);

        List<InvoiceDetailsModel> detailsToAdd = new ArrayList<>();

            ProductModel productToAdd = new ProductModel();
        for (InvoiceDetailsModel invoiceDetail: newData.getInvoiceDetails()
             ) {
            // Ver si me mandan por id o  por Code
            if (invoiceDetail.getProductModel().getId() != 0 && invoiceDetail.getProductModel().getCode() == null) {
                ProductModel productToAddById = productService.findById(invoiceDetail.getProductModel().getId());
                productToAdd = productToAddById;
            }
            if (invoiceDetail.getProductModel().getId() == 0 && invoiceDetail.getProductModel().getCode() != null){
                ProductModel productToAddByCode = productService.findByCode(invoiceDetail.getProductModel().getCode());
                productToAdd = productToAddByCode;
            }
            if (invoiceDetail.getProductModel().getId() != 0 && invoiceDetail.getProductModel().getCode() != null) {
                ProductModel productToAddById = productService.findById(invoiceDetail.getProductModel().getId());
                // Valido que el codigo y el id que mandan por front se correspondan.
                if (!productToAddById.getCode().equals(invoiceDetail.getProductModel().getCode())){
                    throw new IllegalArgumentException("the id does not correspond to this product code " +
                            invoiceDetail.getProductModel().getId() );
                }
                productToAdd = productToAddById;
            }
            // Voy creando un nuevo detail y le agrego los datos que necesito
            InvoiceDetailsModel newDetail = new InvoiceDetailsModel();
            newDetail.setProductModel(productToAdd);
            newDetail.setAmount(invoiceDetail.getAmount());
            newDetail.setInvoiceModel(invoiceSaved);
            newDetail.setSubTotal(invoiceDetail.getAmount() * productToAdd.getPrice());
            // Creo la lista y voy agregando cada detail fuera del ciclo agrego la lista al invoice
            InvoiceDetailsModel newDetailToAdd = this.invoiceDetailsService.create(newDetail);
            detailsToAdd.add(newDetailToAdd);
        }

        // Ahora tengo que actualizar el total del invoice segun los details nuevos.
        newInvoice.setInvoiceDetails(detailsToAdd);
        double totalPrice = 0;
        for (InvoiceDetailsModel item: detailsToAdd
             ) {
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
            detailsDTO.setCode(item.getProductModel().getCode());
            // Voy agregando cada detalle
            products.add(detailsDTO);
        }
        //Agrego la lista de productos ya depurados de lo que quiero mostras
        invoiceDTO.setProducts(products);
        return invoiceDTO;
    }

    public void validateInvoicesDetails  (List<InvoiceDetailsModel> newInvoicesDetailList)
            throws Exception{
        //Validacion de que no llegue vacio la lista
     if (newInvoicesDetailList.size() == 0) {
         throw new InvoiceDetailsNotFoundException("the invoice detail list is empty");
     }
        //Creo un hashset auxiliar para ver si hay productos en la lista repetidos
        Set<InvoiceDetailsModel> setAux = new HashSet<>();
        for (InvoiceDetailsModel newDetail: newInvoicesDetailList
             ) {
            //Validacion que exista el producto, si no existe el service arroja el error
            this.productService.findById(newDetail.getProductModel().getId());
            //Validacion de stock
            if( newDetail.getAmount() > this.productService.findById(newDetail.getProductModel().getId()).getStock()) {
                throw new InsufficientStockException("Insufficient stock in product ID=" + newDetail.getProductModel().getId() );
            }
            setAux.add(newDetail);
        }
        if(setAux.size() != newInvoicesDetailList.size()) {
            throw new IllegalArgumentException("there are duplicate products in the list");
        }


    }


}


