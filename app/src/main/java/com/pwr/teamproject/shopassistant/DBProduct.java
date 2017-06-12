package com.pwr.teamproject.shopassistant;

/**
 * Created by mokry on 12-Jun-17.
 */

public class DBProduct {

    private int storeProductID;
    private String name;
    private double price;
    private String storeName;
    private String storeAddress;
    private double lat;
    private double lng;

    public int getStoreProductID() {
        return storeProductID;
    }

    public void setStoreProductID(int storeProductID) {
        this.storeProductID = storeProductID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreAddress() {
        return storeAddress;
    }

    public void setStoreAddress(String storeAddress) {
        this.storeAddress = storeAddress;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

}
