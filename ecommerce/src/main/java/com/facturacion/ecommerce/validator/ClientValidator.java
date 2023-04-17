package com.facturacion.ecommerce.validator;

import com.facturacion.ecommerce.persistence.model.ClientModel;
import org.springframework.stereotype.Component;

@Component
public class ClientValidator {

    public void validate(ClientModel client) {
        if (client == null) {
            throw new IllegalArgumentException("The client is null o invalided Argument");
        }

        if(client.getDoc().length() < 8) {
            throw new IllegalArgumentException("DNI no valido");
        }

        this.validateStringData("name", client.getName());
        this.validateStringData("lastname", client.getLastname());
        this.validateStringData("doc", client.getDoc());
    }

    private void validateStringData(String attribute, String stringData) {
        if(stringData.isBlank()) {
            throw new IllegalArgumentException("El campo " + attribute + " no es valido o vacio");
        }


    }


}
