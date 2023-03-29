package com.facturacion.ecommerce.persistence.model;

import javax.persistence.*;

@Entity
@Table(name = "invoice_details")
public class InvoiceDetailsModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int invoice_detail_id;

    @ManyToOne
    private InvoiceModel invoiceModel;

    @ManyToOne
    private ProductModel productModel;

    private int amount;

    private double price;

    public InvoiceDetailsModel() {
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

    public InvoiceModel getInvoice() {
        return invoiceModel;
    }

    public void setInvoice(InvoiceModel invoiceModel) {
        this.invoiceModel = invoiceModel;
    }

    public ProductModel getProduct() {
        return productModel;
    }

    public void setProduct(ProductModel productModel) {
        this.productModel = productModel;
    }

    @Override
    public String toString() {
        return "InvoiceDetails{" +
                "invoice_detail_id=" + invoice_detail_id +
                ", invoice=" + invoiceModel +
                ", product=" + productModel +
                ", amount=" + amount +
                ", price=" + price +
                '}';
    }
}

