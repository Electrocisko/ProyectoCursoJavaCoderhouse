package com.facturacion.ecommerce.persistence.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "invoice_details")
public class InvoiceDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int invoice_detail_id;

    @ManyToOne
    private Invoice invoice;

    @ManyToOne
    private Product product;

    private int amount;

    private double price;

    public InvoiceDetails() {
    }

    public int getInvoice_detail_id() {
        return invoice_detail_id;
    }

    public void setInvoice_detail_id(int invoice_detail_id) {
        this.invoice_detail_id = invoice_detail_id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return "InvoiceDetails{" +
                "invoice_detail_id=" + invoice_detail_id +
                ", invoice=" + invoice +
                ", product=" + product +
                ", amount=" + amount +
                ", price=" + price +
                '}';
    }
}

