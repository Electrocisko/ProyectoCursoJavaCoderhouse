package com.facturacion.ecommerce.persistence.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "invoice")
public class InvoiceModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "client_id")
    private ClientModel client_id;

    @Column(name = "created_at")
    private LocalDate created;

    @JsonManagedReference
    @OneToMany(mappedBy = "invoiceModel",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<InvoiceDetailsModel> invoiceDetails;

    private double total;

}
