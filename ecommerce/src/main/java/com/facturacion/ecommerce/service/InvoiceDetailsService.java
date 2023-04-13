package com.facturacion.ecommerce.service;

import com.facturacion.ecommerce.exception.InsufficientStockException;
import com.facturacion.ecommerce.exception.InvoiceNotFoundException;;
import com.facturacion.ecommerce.persistence.model.InvoiceDetailsModel;
import com.facturacion.ecommerce.persistence.repository.InvoiceDetailsRepository;
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
    private ProductService productService;

    public InvoiceDetailsModel create(InvoiceDetailsModel newDetails) throws InsufficientStockException {

        Integer stock = newDetails.getProductModel().getStock();
        Integer amountToAdd = newDetails.getAmount();
        if (amountToAdd > stock) {
            throw new InsufficientStockException("Insufficient stock in product ID=" + newDetails.getProductModel().getId());
        }
        newDetails.getProductModel().setStock(stock-amountToAdd);
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

}
