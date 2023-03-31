package com.facturacion.ecommerce.service;

import com.facturacion.ecommerce.exception.InvoiceNotFoundException;
import com.facturacion.ecommerce.persistence.model.InvoiceModel;
import com.facturacion.ecommerce.persistence.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    public InvoiceModel create(InvoiceModel newInvoice) {
        return this.invoiceRepository.save(newInvoice);
    }

    public List<InvoiceModel> findAll(){
        return this.invoiceRepository.findAll();
    }

    public InvoiceModel findById(Integer id) throws Exception{
        if(id <= 0) {
            throw new Exception("the id is not valid");
        }
        Optional<InvoiceModel> invoiceOp = this.invoiceRepository.findById(id);
        if (invoiceOp.isEmpty()) {
           throw new InvoiceNotFoundException("invoice not found with this id");
        }
        return invoiceOp.get();
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
        this.invoiceRepository.deleteById(id);
        return "Invoice Eliminado";
    }
}


