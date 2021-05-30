package com.example.safeyourfamily.data;

public class DataObserved {
    public String name;
    public String sensor_action;
    public String sensor_rele;
    public String time_action;
    public String time_rele;

    public DataObserved(String name, String sensor_action, String sensor_rele, String time_action, String time_rele) {
        this.name = name;
        this.sensor_action = sensor_action;
        this.sensor_rele = sensor_rele;
        this.time_action = time_action;
        this.time_rele = time_rele;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSensor_action() {
        return sensor_action;
    }

    public void setSensor_action(String sensor_action) {
        this.sensor_action = sensor_action;
    }

    public String getSensor_rele() {
        return sensor_rele;
    }

    public void setSensor_rele(String sensor_rele) {
        this.sensor_rele = sensor_rele;
    }

    public String getTime_action() {
        return time_action;
    }

    public void setTime_action(String time_action) {
        this.time_action = time_action;
    }

    public String getTime_rele() {
        return time_rele;
    }

    public void setTime_rele(String time_rele) {
        this.time_rele = time_rele;
    }


    public String toJsonString() {
        return "{\"name\":\"" + name + "\" , \"sensor_action\":\"" + sensor_action + "\" , \"sensor_rele\":\""+ sensor_rele  + "\" , \"time_action\":\""+ time_action + "\" , \"time_rele\":\""+ time_rele + "\"}";
    }


}
