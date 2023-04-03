package com.facturacion.ecommerce.persistence.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;


@Data
@Entity
@Table(name = "invoice")
public class InvoiceModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private ClientModel client_id;

    @Column(name = "created_at")
    private LocalDate created;

    private double total;


}
