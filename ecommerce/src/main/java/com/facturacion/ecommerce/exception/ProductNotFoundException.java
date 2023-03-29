package com.facturacion.ecommerce.exception;

public class ProductNotFoundException extends  Exception{
    public ProductNotFoundException(String msg) {
        super(msg);
    }
}
