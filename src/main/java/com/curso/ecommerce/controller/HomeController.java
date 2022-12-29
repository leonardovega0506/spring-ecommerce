package com.curso.ecommerce.controller;

import com.curso.ecommerce.model.Producto;
import com.curso.ecommerce.service.ProductoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/")
public class HomeController {
    private final Logger log = LoggerFactory.getLogger(HomeController.class);
    @Autowired
    private ProductoService sProducto;

    @GetMapping("")
    public String home(Model model){
        model.addAttribute("productos",sProducto.finAll());
        return "usuario/home";
    }
    @GetMapping("productohome/{id}")
    public String productoHome(@PathVariable Integer id, Model model){
        log.info("Id Producto enviado como Parametro {}",id);
        Producto producto;
        Optional<Producto> productoOptional = sProducto.get(id);
        producto= productoOptional.get();
        log.info("Resultado del producto traido {}",producto);
        model.addAttribute("producto",producto);
        return "usuario/productohome";
    }

    @PostMapping("/cart")
    public String addCart(){
        return "usuario/carrito";
    }

}
