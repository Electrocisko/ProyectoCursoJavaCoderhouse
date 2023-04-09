package com.facturacion.ecommerce.service;

import com.facturacion.ecommerce.exception.InvoiceNotFoundException;
import com.facturacion.ecommerce.persistence.model.InvoiceDetailsModel;
import com.facturacion.ecommerce.persistence.model.InvoiceModel;
import com.facturacion.ecommerce.persistence.repository.InvoiceDetailsRepository;
import com.facturacion.ecommerce.persistence.repository.InvoiceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Slf4j
@Service
public class InvoiceDetailsService {

    @Autowired
    private InvoiceDetailsRepository invoiceDetailsRepository;



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
        detailsUpdated.setPrice(newData.getPrice());
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
        this.invoiceDetailsRepository.deleteById(id);
        return "Invoice details eliminado";
    }
}
