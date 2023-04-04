package com.facturacion.ecommerce.persistence.model;

import lombok.Data;

import javax.persistence.*;
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


}

