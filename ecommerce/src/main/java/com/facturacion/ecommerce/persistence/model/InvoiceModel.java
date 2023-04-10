package com.facturacion.ecommerce.persistence.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;


@Entity
@Table(name = "invoice")
public class InvoiceModel {

    public InvoiceModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ClientModel getClient_id() {
        return client_id;
    }

    public void setClient_id(ClientModel client_id) {
        this.client_id = client_id;
    }

    public LocalDate getCreated() {
        return created;
    }

    public void setCreated(LocalDate created) {
        this.created = created;
    }

    public List<InvoiceDetailsModel> getInvoiceDetails() {
        return invoiceDetails;
    }

    public void setInvoiceDetails(List<InvoiceDetailsModel> invoiceDetails) {
        this.invoiceDetails = invoiceDetails;
    }


    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

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
