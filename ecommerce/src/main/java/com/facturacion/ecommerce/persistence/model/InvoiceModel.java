package com.facturacion.ecommerce.persistence.model;

import javax.persistence.*;

@Entity
public class InvoiceModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private ClientModel client;

    @Column(name = "created_at")
    private String created;

    private double total;

    public InvoiceModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ClientModel getClient() {
        return client;
    }

    public void setClient(ClientModel client) {
        this.client = client;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "id=" + id +
                ", client=" + client +
                ", created='" + created + '\'' +
                ", total=" + total +
                '}';
    }
}
