package com.curso.ecommerce.service;

import com.curso.ecommerce.model.Orden;
import com.curso.ecommerce.model.Usuario;

import java.util.List;
import java.util.Optional;

public interface OrdenService {

    Orden save(Orden orden);
    List<Orden> findAll();
    String generateNumeroOrden();
    List<Orden> findByUsuario(Usuario usuario);
    Optional<Orden> findById(Integer id);
}
