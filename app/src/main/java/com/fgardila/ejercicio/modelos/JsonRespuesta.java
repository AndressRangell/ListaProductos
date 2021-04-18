package com.fgardila.ejercicio.modelos;

import java.util.ArrayList;
import java.util.List;

public class JsonRespuesta {

    ArrayList<Usuarios> usuarios;
    ArrayList<Productos> productos;

    public JsonRespuesta() {
    }

    public JsonRespuesta(ArrayList<Usuarios> usuarios, ArrayList<Productos> productos) {
        this.usuarios = usuarios;
        this.productos = productos;
    }

    public ArrayList<Usuarios> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(ArrayList<Usuarios> usuarios) {
        this.usuarios = usuarios;
    }

    public ArrayList<Productos> getProductos() {
        return productos;
    }

    public void setProductos(ArrayList<Productos> productos) {
        this.productos = productos;
    }
}
