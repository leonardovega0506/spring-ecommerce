package com.curso.ecommerce.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "ordenes")
public class Orden {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String numeroOrden;
    private Date fechaCreacion;

    private Date fechaRecibida;
    private double total;
    @ManyToOne
    private Usuario usuario;

    @OneToOne(mappedBy = "orden")
    private DetalleOrden detalle;

    public Orden() {
    }

    public Orden(Integer id, String numeroOrden, Date fechaCreacion, Date fechaRecibida, double total, Usuario usuario) {
        this.id = id;
        this.numeroOrden = numeroOrden;
        this.fechaCreacion = fechaCreacion;
        this.fechaRecibida = fechaRecibida;
        this.total = total;
        this.usuario = usuario;
    }

    public Orden(Integer id, String numeroOrden, Date fechaCreacion, Date fechaRecibida, double total) {
        this.id = id;
        this.numeroOrden = numeroOrden;
        this.fechaCreacion = fechaCreacion;
        this.fechaRecibida = fechaRecibida;
        this.total = total;
    }
}
