package com.facturacion.ecommerce.dto;

import java.util.ArrayList;
import java.util.List;

public class InvoiceDTO {

    private int id;

    private int client_id;

    private String clientName;

    private List<DetailsDTO> products;

    private double total;

    public InvoiceDTO() {
    }

    public InvoiceDTO(int id, int client_id, String clientName, List products, double total) {
        this.id = id;
        this.client_id = client_id;
        this.clientName = clientName;
        this.products = products;
        this.total = total;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public List<DetailsDTO> getProducts() {
        return products;
    }

    public void setProducts(List<DetailsDTO> products) {
        this.products = products;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "InvoiceDTO{" +
                "id=" + id +
                ", client_id=" + client_id +
                ", clientName='" + clientName + '\'' +
                ", products=" + products +
                ", total=" + total +
                '}';
    }

}
