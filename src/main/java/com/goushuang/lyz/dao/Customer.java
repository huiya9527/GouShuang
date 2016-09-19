package com.goushuang.lyz.dao;

public class Customer {
    private int id;
    private String name;
    private String password;
    private float reserve;
    private String info;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public float getReserve() {
        return reserve;
    }

    public void setReserve(float reserve) {
        this.reserve = reserve;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
