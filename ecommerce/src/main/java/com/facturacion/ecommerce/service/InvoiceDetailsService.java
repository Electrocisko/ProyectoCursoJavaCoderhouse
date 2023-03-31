package com.facturacion.ecommerce.service;

import com.facturacion.ecommerce.persistence.model.InvoiceDetailsModel;
import com.facturacion.ecommerce.persistence.repository.InvoiceDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InvoiceDetailsService {

    @Autowired
    private InvoiceDetailsRepository invoiceDetailsRepository;

    public InvoiceDetailsModel create(InvoiceDetailsModel newDetails) {
        return this.invoiceDetailsRepository.save(newDetails);
    }

    public List<InvoiceDetailsModel>  findAll() {
        return  this.invoiceDetailsRepository.findAll();
    }

    public InvoiceDetailsModel findById(Integer id) {
        return this.invoiceDetailsRepository.getById(id);
    }

    public InvoiceDetailsModel updated(InvoiceDetailsModel newData, Integer id) {
        Optional<InvoiceDetailsModel> detailOp = this.invoiceDetailsRepository.findById(id);
        if(detailOp.isEmpty()) {
            System.out.println("Esta vacio");
        }
            InvoiceDetailsModel detailsUpdated = detailOp.get();
        detailsUpdated.setInvoiceModel(newData.getInvoiceModel());
        detailsUpdated.setProductModel(newData.getProductModel());
        detailsUpdated.setAmount(newData.getAmount());
        detailsUpdated.setPrice(newData.getPrice());
        return this.invoiceDetailsRepository.save(detailsUpdated);
    }
}
