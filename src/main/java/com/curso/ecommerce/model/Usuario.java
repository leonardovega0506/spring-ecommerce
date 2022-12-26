package com.curso.ecommerce.model;

import jakarta.persistence.Entity;
import lombok.Data;

@Data
public class Usuario {

    private Integer idUsuario;

    private String nombre;

    private String userName;

    private String email;

    private String direccion;

    private String telefono;

    private String tipo;

    private String password;
}
