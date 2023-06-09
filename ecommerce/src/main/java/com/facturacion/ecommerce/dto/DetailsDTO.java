package com.facturacion.ecommerce.dto;

public class DetailsDTO {

    String product;
    String code;
    Integer amount;
    Double price;
    Double subTotal;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "DetailsDTO{" +
                "product='" + product + '\'' +
                ", amount=" + amount +
                ", price=" + price +
                ", subTotal=" + subTotal +
                ", code='" + code + '\'' +
                '}';
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(Double subTotal) {
        this.subTotal = subTotal;
    }
}
