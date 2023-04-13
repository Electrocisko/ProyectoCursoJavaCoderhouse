package com.facturacion.ecommerce.dto;

import java.util.ArrayList;
import java.util.List;

public class ClientDTO {
    private int id;
    private String completeName;
    private String document;
    private List<Integer> invoicesId = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCompleteName() {
        return completeName;
    }

    public void setCompleteName(String completeName) {
        this.completeName = completeName;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public List<Integer> getInvoicesId() {
        return invoicesId;
    }

    public void setInvoicesId(List<Integer> invoicesId) {
        this.invoicesId = invoicesId;
    }
}
