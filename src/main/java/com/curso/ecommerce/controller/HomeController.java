package com.curso.ecommerce.controller;

import com.curso.ecommerce.model.DetalleOrden;
import com.curso.ecommerce.model.Orden;
import com.curso.ecommerce.model.Producto;
import com.curso.ecommerce.service.ProductoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class HomeController {
    private final Logger log = LoggerFactory.getLogger(HomeController.class);
    @Autowired
    private ProductoService sProducto;

    //Para almacenar los detalles de la orden
    List<DetalleOrden> detalles = new ArrayList<DetalleOrden>();

    //Datos de la orden
    Orden orden = new Orden();

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
    public String addCart(@RequestParam Integer id, @RequestParam Integer cantidad, Model model){
        DetalleOrden detalleOrden = new DetalleOrden();
        Producto producto;
        double sumaTotal;
        Optional<Producto> optionalProducto = sProducto.get(id);
        log.info("Producto añadido: {}",optionalProducto.get());
        log.info("Cantidad: {}",cantidad);
        producto = optionalProducto.get();

        detalleOrden.setCantidad(cantidad);
        detalleOrden.setPrecio(producto.getPrecio());
        detalleOrden.setNombre(producto.getNombre());
        detalleOrden.setTotal(producto.getPrecio()*cantidad);
        detalleOrden.setProducto(producto);

        //Validar que el producto no se añada 2 veces
        Integer idProducto = producto.getId();
        boolean ingresado = detalles.stream().anyMatch(p -> p.getProducto().getId().equals(idProducto));
        if(!ingresado){
            detalles.add(detalleOrden);
        }


        sumaTotal= detalles.stream().mapToDouble(dt -> dt.getTotal()).sum();

        orden.setTotal(sumaTotal);
        model.addAttribute("cart", detalles);
        model.addAttribute("orden",orden);
        return "usuario/carrito";
    }

    //Quitar un producto del carrito
    @GetMapping("delete/cart/{id}")
    public String deleteProductoCart(@PathVariable Integer id, Model model){

        //Lista nueva de productos
        List<DetalleOrden> ordenesNuevas = new ArrayList<DetalleOrden>();

        for(DetalleOrden detalleOrden:detalles){
            if(detalleOrden.getProducto().getId() != id){
              ordenesNuevas.add(detalleOrden);
            }
        }

        //Poner la nueva lista con los productos restantes
        detalles = ordenesNuevas;
        double sumaTotal = 0;
        sumaTotal= detalles.stream().mapToDouble(dt -> dt.getTotal()).sum();

        orden.setTotal(sumaTotal);
        model.addAttribute("cart", detalles);
        model.addAttribute("orden",orden);
        return "usuario/carrito";
    }

    @GetMapping("getCart")
    public String getCart(Model model){
        model.addAttribute("cart", detalles);
        model.addAttribute("orden",orden);
        return "usuario/carrito";
    }

}
