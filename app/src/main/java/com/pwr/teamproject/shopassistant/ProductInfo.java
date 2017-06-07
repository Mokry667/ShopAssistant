package com.pwr.teamproject.shopassistant;

/**
 * Created by mokry on 07-Jun-17.
 */

public class ProductInfo {

    private double price;
    private String distance;
    private String time;

    ProductInfo(double price, String distance, String time){
        this.price = price;
        this.distance = distance;
        this.time = time;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }



}
