package com.curso.ecommerce.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idUsuario;

    private String nombre;
    private String userName;
    private String email;
    private String direccion;
    private String telefono;
    private String tipo;
    private String password;

    public Usuario(Integer idUsuario, String nombre, String userName, String email, String direccion, String telefono, String tipo, String password, List<Producto> listaProductos, List<Orden> listaOrdenes) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.userName = userName;
        this.email = email;
        this.direccion = direccion;
        this.telefono = telefono;
        this.tipo = tipo;
        this.password = password;
        this.listaProductos = listaProductos;
        this.listaOrdenes = listaOrdenes;
    }

    public Usuario(Integer idUsuario, String nombre, String userName, String email, String direccion, String telefono, String tipo, String password) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.userName = userName;
        this.email = email;
        this.direccion = direccion;
        this.telefono = telefono;
        this.tipo = tipo;
        this.password = password;
    }

    public Usuario() {
    }

    @OneToMany(mappedBy = "usuario")
    private List<Producto> listaProductos;

    @OneToMany(mappedBy = "usuario")
    private List<Orden> listaOrdenes;
}
