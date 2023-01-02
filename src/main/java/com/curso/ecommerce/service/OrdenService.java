package com.curso.ecommerce.service;

import com.curso.ecommerce.model.Orden;

import java.util.List;

public interface OrdenService {

    Orden save(Orden orden);
    List<Orden> findAll();
    String generateNumeroOrden();
}
