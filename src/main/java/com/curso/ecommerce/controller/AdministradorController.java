package com.curso.ecommerce.controller;

import com.curso.ecommerce.model.Producto;
import com.curso.ecommerce.service.OrdenService;
import com.curso.ecommerce.service.ProductoService;
import com.curso.ecommerce.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
@RequestMapping("/administrador")
public class AdministradorController {
    @Autowired
    private ProductoService productoService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private OrdenService ordenService;

    @GetMapping("")
    public String home(Model model){
        List<Producto> productos = productoService.finAll();
        model.addAttribute("productos",productos);
        return "administrador/home.html";
    }
    @GetMapping("/usuarios")
    public String usuarios(Model model){
        model.addAttribute("usuarios",usuarioService.findAll());
        return "administrador/usuarios";
    }

    @GetMapping("/ordenes")
    public String ordenes(Model model){
        model.addAttribute("ordenes",ordenService.findAll());
        return "administrador/ordenes";
    }

}
