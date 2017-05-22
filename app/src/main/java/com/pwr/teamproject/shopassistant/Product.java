package com.pwr.teamproject.shopassistant;

/**
 * Created by mokry on 22-May-17.
 */

public class Product {

    private int id;
    private String name;
    private String desc;
    private int prodCategoryId;
    private String prodCategory;
    private String photo;

    Product(int id, String name, String desc, int prodCategoryId, String prodCategory, String photo){
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.prodCategoryId = prodCategoryId;
        this.prodCategory = prodCategory;
        this.photo = photo;
    }

    public int getProdCategoryId() {
        return prodCategoryId;
    }

    public void setProdCategoryId(int prodCategoryId) {
        this.prodCategoryId = prodCategoryId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getProdCategory() {
        return prodCategory;
    }

    public void setProdCategory(String prodCategory) {
        this.prodCategory = prodCategory;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
