package com.facturacion.ecommerce.persistence.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@Data
@Entity
@Table(name = "invoice_details")

public class InvoiceDetailsModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int invoice_detail_id;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "invoice_id")
    private InvoiceModel invoiceModel;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductModel productModel;

    private int amount;

    @Column(name = "price")
    private double subTotal;


}


