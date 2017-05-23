package com.pwr.teamproject.shopassistant;

/**
 * Created by mokry on 23-May-17.
 */

public class ProdCategory {

    private int id;
    private String name;

    public ProdCategory(int id, String name) {
        this.id = id;
        this.name = name;
    }

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
}
