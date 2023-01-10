package com.curso.ecommerce.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "orden")
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumeroOrden() {
        return numeroOrden;
    }

    public void setNumeroOrden(String numeroOrden) {
        this.numeroOrden = numeroOrden;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaRecibida() {
        return fechaRecibida;
    }

    public void setFechaRecibida(Date fechaRecibida) {
        this.fechaRecibida = fechaRecibida;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public DetalleOrden getDetalle() {
        return detalle;
    }

    public void setDetalle(DetalleOrden detalle) {
        this.detalle = detalle;
    }

    @Override
    public String toString() {
        return "Orden{" +
                "id=" + id +
                ", numeroOrden='" + numeroOrden + '\'' +
                ", fechaCreacion=" + fechaCreacion +
                ", fechaRecibida=" + fechaRecibida +
                ", total=" + total +
                ", usuario=" + usuario +
                ", detalle=" + detalle +
                '}';
    }
}
