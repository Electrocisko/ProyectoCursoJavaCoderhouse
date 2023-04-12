package com.facturacion.ecommerce.service;

import com.facturacion.ecommerce.exception.InvoiceNotFoundException;
import com.facturacion.ecommerce.exception.ProductNotFoundException;
import com.facturacion.ecommerce.persistence.model.InvoiceDetailsModel;
import com.facturacion.ecommerce.persistence.model.InvoiceModel;
import com.facturacion.ecommerce.persistence.model.ProductModel;
import com.facturacion.ecommerce.persistence.repository.InvoiceDetailsRepository;
import com.facturacion.ecommerce.persistence.repository.InvoiceRepository;
import com.facturacion.ecommerce.persistence.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;


@Slf4j
@Service
public class InvoiceDetailsService {

    @Autowired
    private InvoiceDetailsRepository invoiceDetailsRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private InvoiceRepository invoiceRepository;

    public InvoiceDetailsModel create(InvoiceDetailsModel newDetails) throws Exception {

       return this.invoiceDetailsRepository.save(newDetails);
    }


    public List<InvoiceDetailsModel>  findAll() {
        return  this.invoiceDetailsRepository.findAll();
    }

    public InvoiceDetailsModel findById(Integer id) throws Exception {
        if(id <= 0) {
            throw new Exception("the id is not valid");
        }
        Optional<InvoiceDetailsModel> invoiceDetailOp = this.invoiceDetailsRepository.findById(id);
        if (invoiceDetailOp.isEmpty()) {
            throw new InvoiceNotFoundException("invoice detail not found with this id");
        }
        return this.invoiceDetailsRepository.getById(id);
    }

    public InvoiceDetailsModel updated(InvoiceDetailsModel newData, Integer id)  throws  Exception{
        if(id <= 0) {
            throw new Exception("the id is not valid");
        }
        Optional<InvoiceDetailsModel> invoiceDetailOp = this.invoiceDetailsRepository.findById(id);
        if (invoiceDetailOp.isEmpty()) {
            throw new InvoiceNotFoundException("invoice detail not found with this id");
        }
        InvoiceDetailsModel detailsUpdated = invoiceDetailOp.get();
        detailsUpdated.setInvoiceModel(newData.getInvoiceModel());
        detailsUpdated.setProductModel(newData.getProductModel());
        detailsUpdated.setAmount(newData.getAmount());
        detailsUpdated.setSubTotal(newData.getSubTotal());
        return this.invoiceDetailsRepository.save(detailsUpdated);
    }

    public String deleteById(Integer id) throws Exception{
        if(id <= 0) {
            throw new Exception("the id is not valid");
        }
        Optional<InvoiceDetailsModel> invoiceDetailOp = this.invoiceDetailsRepository.findById(id);
        if (invoiceDetailOp.isEmpty()) {
            throw new InvoiceNotFoundException("invoice detail not found with this id");
        }
        //Dejeo el borrar comentado, eso solo por utilizacion dictica
        //this.invoiceDetailsRepository.deleteById(id);
        return "Invoice details they cannot be deleted. This is just a teaching sample ";
    }
}
