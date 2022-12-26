package com.curso.ecommerce.model;

import lombok.Data;

import java.util.Date;

@Data
public class Orden {

    private Integer id;
    private String numeroOrden;
    private Date fechaCreacion;

    private Date fechaRecibida;
    private double total;

    public Orden() {
    }

    public Orden(Integer id, String numeroOrden, Date fechaCreacion, Date fechaRecibida, double total) {
        this.id = id;
        this.numeroOrden = numeroOrden;
        this.fechaCreacion = fechaCreacion;
        this.fechaRecibida = fechaRecibida;
        this.total = total;
    }
}
