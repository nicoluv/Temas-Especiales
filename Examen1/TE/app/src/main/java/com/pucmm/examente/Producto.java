package com.pucmm.examente;

public class Producto {

    String articulo;
    String descripcion;
    int precio;

    public Producto(String articulo, String descripcion, int precio) {
        this.articulo = articulo;
        this.descripcion = descripcion;
        this.precio = precio;
    }


    public String getArticulo() {
        return articulo;
    }

    public void setArticulo(String articulo) {
        this.articulo = articulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }
}

