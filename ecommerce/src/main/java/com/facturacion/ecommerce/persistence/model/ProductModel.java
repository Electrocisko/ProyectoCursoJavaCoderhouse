package com.facturacion.ecommerce.persistence.model;

import lombok.Data;

import javax.persistence.*;
@Data
@Entity
@Table(name = "products")
public class ProductModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String description;
    private String code;
    private int stock;
    private double price;
    private boolean active = true;
}
