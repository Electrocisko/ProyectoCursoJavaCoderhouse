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

    public InvoiceDTO createById(InvoiceModel newData) throws Exception {
        InvoiceModel newInvoice = new InvoiceModel();
        newInvoice.setCreated(LocalDate.now());
        //Obtengo Id del cliente
        Integer clientId = newData.getClient_id().getId();
       //Busco al cliente
        ClientModel clientToAdd = clientService.findById(clientId);
        newInvoice.setClient_id(clientToAdd);
        // Valido los datos que llegan del front
        this.validateInvoicesDetailsById(newData.getInvoiceDetails());
        //Creo un invoiceSaved antes de retornar para obtener el id asignado al invoice recien creado
        InvoiceModel invoiceSaved = this.invoiceRepository.save(newInvoice);
        List<InvoiceDetailsModel> detailsToAdd = new ArrayList<>();

        for (InvoiceDetailsModel invoiceDetail: newData.getInvoiceDetails()
             ) {
                ProductModel productToAdd = productService.findById(invoiceDetail.getProductModel().getId());
            // Voy creando un nuevo detail y le agrego los datos que necesito
            InvoiceDetailsModel newDetail = new InvoiceDetailsModel();
            newDetail.setProductModel(productToAdd);
            newDetail.setAmount(invoiceDetail.getAmount());
            newDetail.setPrice(productToAdd.getPrice());
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
            totalPrice = totalPrice + (item.getAmount() * item.getProductModel().getPrice());
        }
       newInvoice.setTotal(totalPrice);
        this.invoiceRepository.save(newInvoice);
        //Creo el invoiceDTO
        InvoiceDTO invoiceDTO = this.returnInvoiceDTO(newInvoice);
        List<DetailsDTO> products = new ArrayList<>();
        // Recorro el invoices details original, para crear nuevos detailsDTO e incluirlos en el listado
        for (InvoiceDetailsModel item : newInvoice.getInvoiceDetails()) {
            products.add(this.returnDetailsDTO(item));
        }
        //Agrego la lista de productos ya depurados de lo que quiero mostrar
        invoiceDTO.setProducts(products);
        return invoiceDTO;
    }

    public InvoiceModel createByCode(InvoiceModel newData) throws Exception {
        InvoiceModel newInvoice = new InvoiceModel();
        newInvoice.setCreated(LocalDate.now());
        //Obtengo Id del cliente
        String documentClient = newData.getClient_id().getDoc();
        //Busco al cliente
        ClientModel clientToAdd = clientService.findByDocNumber(documentClient);
        newInvoice.setClient_id(clientToAdd);
        // Valido los datos que llegan del front
        this.validateInvoicesDetailsByCode(newData.getInvoiceDetails());
        //Creo un invoiceSaved antes de retornar para obtener el id asignado al invoice recien creado
        InvoiceModel invoiceSaved = this.invoiceRepository.save(newInvoice);
        List<InvoiceDetailsModel> detailsToAdd = new ArrayList<>();
        for (InvoiceDetailsModel invoiceDetail: newData.getInvoiceDetails()
        ) {
            ProductModel productToAdd = productService.findByCode(invoiceDetail.getProductModel().getCode());
            // Voy creando un nuevo detail y le agrego los datos que necesito
            InvoiceDetailsModel newDetail = new InvoiceDetailsModel();
            newDetail.setProductModel(productToAdd);
            newDetail.setAmount(invoiceDetail.getAmount());
            newDetail.setInvoiceModel(invoiceSaved);
            newDetail.setPrice(productToAdd.getPrice());

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

    public List<InvoiceDTO> findAll(){
        List<InvoiceModel> invoiceModelList = this.invoiceRepository.findAll();

        System.out.println("Lista de invoices" + invoiceModelList);

        //Creo una lista invoiceDTO y le voy recorriendo para asignar los valores
        List<InvoiceDTO> invoiceDTOList = new ArrayList<>();
      for (InvoiceModel  invoiceItem : invoiceModelList) {
          //Creo una lista details y le voy recorriendo para asignar los valores
          List<DetailsDTO> detailsDTOList = new ArrayList<>();
          for (InvoiceDetailsModel detailItem: invoiceItem.getInvoiceDetails()
               ) {
              detailsDTOList.add(this.returnDetailsDTO(detailItem));
          }
          InvoiceDTO invoiceDTO = this.returnInvoiceDTO(invoiceItem);
          // Agrego la lista de details dto al invoice dto
          invoiceDTO.setProducts(detailsDTOList);
          invoiceDTOList.add(invoiceDTO);
      }
        return invoiceDTOList;
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
        // Obtengo el invoide dto
        InvoiceDTO invoiceDTO = this.returnInvoiceDTO(invoice);
        List<DetailsDTO> products = new ArrayList<>();
        // Recorro el invoices details original, para crear nuevos detailsDTO e incluirlos en el listado
        for (InvoiceDetailsModel item : invoice.getInvoiceDetails()) {
            products.add(this.returnDetailsDTO(item));
        }
        //Agrego la lista de productos ya depurados de lo que quiero mostrar
        invoiceDTO.setProducts(products);
        return invoiceDTO;
    }

    public void validateInvoicesDetailsById(List<InvoiceDetailsModel> newInvoicesDetailList)
            throws Exception{
        //Validacion de que no llegue vacio la lista
     if (newInvoicesDetailList.size() == 0) {
         throw new InvoiceDetailsNotFoundException("the invoice detail list is empty");
     }
     //Validaciones duplicados - stock - id
     Set<InvoiceDetailsModel> checkDuplicates = new HashSet<>();
     for (InvoiceDetailsModel newDetail: newInvoicesDetailList
        ) {
             ProductModel checkProduct = this.productService.findById(newDetail.getProductModel().getId());
            //Validacion de stock
            if( newDetail.getAmount() > checkProduct.getStock()) {
                    throw new InsufficientStockException("Insufficient stock in product ID=" + newDetail.getProductModel().getId() );
                }
            checkDuplicates.add(newDetail);
        }
          if(checkDuplicates.size() != newInvoicesDetailList.size())  {
              throw new IllegalArgumentException("Duplicates products in list");
          }
    }

    public void validateInvoicesDetailsByCode (List<InvoiceDetailsModel> newInvoicesDetailList)
            throws Exception {
        //Validacion de que no llegue vacio la lista
        if (newInvoicesDetailList.size() == 0) {
            throw new InvoiceDetailsNotFoundException("the invoice detail list is empty");
        }
        //Validaciones duplicados - stock - id
        Set<InvoiceDetailsModel> checkDuplicates = new HashSet<>();
        for (InvoiceDetailsModel newDetail: newInvoicesDetailList
        ) {
            ProductModel checkProduct = this.productService.findByCode(newDetail.getProductModel().getCode());
            //Validacion de stock
            if( newDetail.getAmount() > checkProduct.getStock()) {
                throw new InsufficientStockException("Insufficient stock in product ID=" + newDetail.getProductModel().getId() );
            }
            checkDuplicates.add(newDetail);
        }
        if(checkDuplicates.size() != newInvoicesDetailList.size())  {
            throw new IllegalArgumentException("Duplicates products in list");
        }
    }

    public InvoiceDTO returnInvoiceDTO(InvoiceModel invoice) {
        InvoiceDTO invoiceDTO = new InvoiceDTO();
        invoiceDTO.setId(invoice.getId());
        invoiceDTO.setClient_id(invoice.getClient_id().getId());
        invoiceDTO.setClientName(invoice.getClient_id().getName() + " " + invoice.getClient_id().getLastname());
        invoiceDTO.setTotal(invoice.getTotal());
      return invoiceDTO;
    }

    public DetailsDTO returnDetailsDTO(InvoiceDetailsModel detail) {
        DetailsDTO detailsDTO = new DetailsDTO();
        detailsDTO.setCode(detail.getProductModel().getCode());
        detailsDTO.setProduct(detail.getProductModel().getDescription());
        detailsDTO.setAmount(detail.getAmount());
        detailsDTO.setPrice(detail.getPrice());
        detailsDTO.setSubTotal(detail.getPrice() * detail.getAmount());

       return detailsDTO;


    }
}




