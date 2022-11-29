package com.example.myapplicationrlv.database;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.ArrayList;

@Entity(tableName = "myProducts")
public class Product implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "pid")
    public int pid;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "price")
    public Double price;

    @ColumnInfo(name = "brand")
    public String brand;

    @ColumnInfo(name = "image")
    String image;


    public Product(String name, Double price, String brand) {
        this.name = name;
        this.price = price;
        this.brand = brand;
    }

    public static ArrayList<Product> createProductsList(int numProducts) {
        ArrayList<Product> contacts = new ArrayList<Product>();

        for (int i = 1; i <= numProducts; i++) {
            contacts.add(new Product("Nombre", 99.9, "Brand"));
        }

        return contacts;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
}
