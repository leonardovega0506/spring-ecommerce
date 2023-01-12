package com.curso.ecommerce.controller;

import com.curso.ecommerce.model.Orden;
import com.curso.ecommerce.model.Producto;
import com.curso.ecommerce.service.OrdenService;
import com.curso.ecommerce.service.ProductoService;
import com.curso.ecommerce.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/administrador")
public class AdministradorController {
    @Autowired
    private ProductoService productoService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private OrdenService ordenService;

    private Logger log = LoggerFactory.getLogger(AdministradorController.class);
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

    @GetMapping("/detalle/{id}")
    public String detalleOrden(@PathVariable Integer id, HttpSession session, Model model){
        log.info("Id de la orden {}",id);
        Optional<Orden> ordenOptional = ordenService.findById(id);
        model.addAttribute("detalles",ordenOptional.get().getDetalle());

        //Session
        model.addAttribute("sesion",session.getAttribute("idUsuario"));

        return "administrador/detalleorden";
    }

}
