package com.example.safeyourfamily.data;

public class Observed {
    public Long id;
    public String phone;
    public String name;
    public String address;

    public Observed(Long id, String phone, String name, String address) {
        this.id = id;
        this.phone = phone;
        this.name = name;
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "{\"id\":" + id + ", \"name\":\"" + name + "\" , \"address\":\"" + address + "\" , \"phone\":\""+ phone + "\"}";
    }
}
