package com.curso.ecommerce.model;

import lombok.Data;

@Data
public class Producto {

    private Integer id;
    private String nombre;
    private String descripcion;
    private String imagen;
    private double precio;
    private int cantidad;

    public Producto(Integer id, String nombre, String descripcion, String imagen, double precio, int cantidad) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.precio = precio;
        this.cantidad = cantidad;
    }

    public Producto() {
    }

}
