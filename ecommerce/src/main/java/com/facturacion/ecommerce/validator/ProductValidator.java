package com.facturacion.ecommerce.validator;

import com.facturacion.ecommerce.persistence.model.ProductModel;

public class ProductValidator {

    public static void validate(ProductModel product) throws IllegalArgumentException{

        if (product == null) {
            throw new IllegalArgumentException("The client is null o invalided Argument");
        }

        validateStringData("description", product.getDescription());
        validateStringData("code", product.getCode());

        if(product.getStock() < 0 ) {
            throw new IllegalArgumentException("stock cannot be negative");
        }

        if(product.getPrice() <= 0.01 ) {
            throw new IllegalArgumentException("Price must be mayor than 0.01");
        }

       if(product.getCode().length() < 3) {
            throw new IllegalArgumentException("Code must have more than three characters");
        }

    }

    private static void validateStringData(String attribute, String stringData) {
        if(stringData.isBlank()) {
            throw new IllegalArgumentException("The  attribute " + attribute + " is not valid or empty");
        }
    }
}
