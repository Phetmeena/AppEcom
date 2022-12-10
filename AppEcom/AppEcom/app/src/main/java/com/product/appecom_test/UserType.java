package com.product.appecom_test;

import java.io.Serializable;

public enum UserType implements Serializable {
    SALESMAN("Salesman"),
    CUSTOMER("Customer");

    private String label;
    private String value;

    UserType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

}
