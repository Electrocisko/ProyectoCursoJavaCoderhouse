package com.facturacion.ecommerce.persistence.model;

import lombok.Data;

import javax.persistence.*;
@Data
@Entity
@Table(name = "invoice")
public class InvoiceModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private ClientModel client;

    @Column(name = "created_at")
    private String created;

    private double total;


}
