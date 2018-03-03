package com.hauday.hauday1.POJO;

import java.io.Serializable;

/**
 * Created by Hauday on 7/23/2016.
 */
public class Food implements Serializable{
    String name,id,price,details,image,materials;

    public Food(String name, String id, String price, String details, String image, String materials) {
        this.name = name;
        this.id = id;
        this.price = price;
        this.details = details;
        this.image = image;
        this.materials = materials;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getMaterials() {
        return materials;
    }

    public void setMaterials(String materials) {
        this.materials = materials;
    }
}






