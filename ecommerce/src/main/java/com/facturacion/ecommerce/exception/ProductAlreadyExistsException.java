package com.facturacion.ecommerce.exception;

public class ProductAlreadyExistsException extends  Exception{
    public ProductAlreadyExistsException(String msg) {
        super(msg);
    }
}
