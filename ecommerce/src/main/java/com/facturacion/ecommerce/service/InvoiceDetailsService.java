package com.facturacion.ecommerce.service;

import com.facturacion.ecommerce.persistence.model.InvoiceDetailsModel;
import com.facturacion.ecommerce.persistence.repository.InvoiceDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InvoiceDetailsService {

    @Autowired
    private InvoiceDetailsRepository invoiceDetailsRepository;

    public InvoiceDetailsModel create(InvoiceDetailsModel newDetails) {
        return this.invoiceDetailsRepository.save(newDetails);
    }
}
