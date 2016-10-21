package com.goushuang.lyz.viewObject;


public enum OrderState {
    notPay("not pay"),
    paid("paid"),
    deliver("deliver"),
    finished("finished"),
    cancelled("cancelled");

    private String description;

    OrderState(final String description){
        this.description = description;
    }

    public String getDescription(){
        return this.description;
    }

}
