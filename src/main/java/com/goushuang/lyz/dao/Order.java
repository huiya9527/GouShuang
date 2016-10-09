package com.goushuang.lyz.dao;

import org.springframework.stereotype.Component;

@Component
public class Order {

    private String userName;
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

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}



