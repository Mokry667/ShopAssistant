package com.pwr.teamproject.shopassistant;

/**
 * Created by mokry on 22-May-17.
 */

public class StoreProduct {

    public int id;
    public int storeID;
    public Store store;
    public int prodID;
    public Product product;
    public int amount;
    public double price;
    public double weight;
    public double PPQ;
    public double PPW;

    public StoreProduct(int id, int storeID, com.pwr.teamproject.shopassistant.Store store, int prodID, Product product, int amount, double price, double weight, double PPQ, double PPW) {
        this.id = id;
        this.storeID = storeID;
        this.store = store;
        this.prodID = prodID;
        this.product = product;
        this.amount = amount;
        this.price = price;
        this.weight = weight;
        this.PPQ = PPQ;
        this.PPW = PPW;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStoreID() {
        return storeID;
    }

    public void setStoreID(int storeID) {
        this.storeID = storeID;
    }

    public com.pwr.teamproject.shopassistant.Store getStore() {
        return store;
    }

    public void setStore(com.pwr.teamproject.shopassistant.Store store) {
        this.store = store;
    }

    public int getProdID() {
        return prodID;
    }

    public void setProdID(int prodID) {
        this.prodID = prodID;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getWeight() {return weight;}

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getPPQ() {
        return PPQ;
    }

    public void setPPQ(double PPQ) {
        this.PPQ = PPQ;
    }

    public double getPPW() {
        return PPW;
    }

    public void setPPW(double PPW) {
        this.PPW = PPW;
    }

}
