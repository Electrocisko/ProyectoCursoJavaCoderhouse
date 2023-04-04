package com.facturacion.ecommerce.exception;

public class ClientAlreadyRegisteredException extends Exception{

    public ClientAlreadyRegisteredException(String msg) {
        super(msg);
    }
}
