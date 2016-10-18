package com.goushuang.lyz.dao;

import org.springframework.stereotype.Component;

@Component
public class SystemOrder {

    private int id;
    private String customer;
    private String courier;
    private float totalPrice = 0;
    private String state;
    private String time;
    private String info = "";

    public void addItem(String name, int count, float price){
        if(info.length() == 0) {
            info +=  (name + "," + count + "," + price);
        } else {
            info += (" "+name + "," + count + "," + price);
        }

        this.totalPrice += (count * price);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getCourier() {
        return courier;
    }

    public void setCourier(String courier) {
        this.courier = courier;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}



