package com.goushuang.lyz.viewObject;

public enum UserType {
    customer("customer"),
    courier("courier"),
    administrator("administrator");

    private String description;

    UserType(final String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }
}
