package com.curso.ecommerce.service;

import com.curso.ecommerce.model.DetalleOrden;
import com.curso.ecommerce.repository.DetalleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DetalleOrdenServiceImpl implements DetalleService{

    @Autowired
    private DetalleRepository detalleRepository;

    @Override
    public DetalleOrden save(DetalleOrden detalleOrden) {
        return detalleRepository.save(detalleOrden);
    }
}
