package com.fgardila.ejercicio.modelos;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class Productos {

    String id;
    String name;
    String description;
    double amount;
    String imgUrl;
    String fechaCompra;

    public Productos() {
    }

    public Productos(String id, String name, String description, double amount, String imgUrl) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.amount = amount;
        this.imgUrl = imgUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getFechaCompra() { return fechaCompra; }

    public void setFechaCompra(String fechaCompra) { this.fechaCompra = fechaCompra; }
}
