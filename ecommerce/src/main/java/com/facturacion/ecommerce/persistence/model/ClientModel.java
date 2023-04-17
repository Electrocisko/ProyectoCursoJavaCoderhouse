package com.facturacion.ecommerce.persistence.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "clients")
public class ClientModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String lastname;
    private String doc;
    private Boolean active = true;

    @JsonManagedReference
    @OneToMany(mappedBy = "client_id", cascade = CascadeType.ALL)
    private List<InvoiceModel> invoiceModel;

}

