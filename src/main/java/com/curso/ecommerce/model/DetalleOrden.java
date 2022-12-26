package com.curso.ecommerce.model;

import lombok.Data;

@Data
public class DetalleOrden {
    private Integer id;
    private String nombre;
    private double cantidad;
    private double precio;
    private double total;

    public DetalleOrden(Integer id, String nombre, double cantidad, double precio, double total) {
        this.id = id;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.precio = precio;
        this.total = total;
    }

    public DetalleOrden() {
    }
}
