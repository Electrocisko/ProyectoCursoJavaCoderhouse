package com.facturacion.ecommerce.service;

import com.facturacion.ecommerce.persistence.model.InvoiceModel;
import com.facturacion.ecommerce.persistence.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
