package com.curso.ecommerce.controller;

import com.curso.ecommerce.model.DetalleOrden;
import com.curso.ecommerce.model.Orden;
import com.curso.ecommerce.model.Producto;
import com.curso.ecommerce.model.Usuario;
import com.curso.ecommerce.service.DetalleService;
import com.curso.ecommerce.service.OrdenService;
import com.curso.ecommerce.service.ProductoService;
import com.curso.ecommerce.service.UsuarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/")
public class HomeController {
    private final Logger log = LoggerFactory.getLogger(HomeController.class);
    @Autowired
    private ProductoService sProducto;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private OrdenService ordenService;

    @Autowired
    private DetalleService detalleService;

    //Para almacenar los detalles de la orden
    List<DetalleOrden> detalles = new ArrayList<DetalleOrden>();

    //Datos de la orden
    Orden orden = new Orden();

    @GetMapping("")
    public String home(Model model, HttpSession session){
        log.info("SESSION DEL USUARIO: {}", session.getAttribute("idUsuario"));
        model.addAttribute("productos",sProducto.finAll());

        //session
        model.addAttribute("sesion",session.getAttribute("idUsuario"));
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
    public String getCart(Model model, HttpSession session){
        model.addAttribute("cart", detalles);
        model.addAttribute("orden",orden);
        model.addAttribute("sesion",session.getAttribute("idUsuario"));
        return "usuario/carrito";
    }

    @GetMapping("/order")
    public String order(Model model, HttpSession session){
        Usuario usuario = usuarioService.findById(Integer.parseInt(session.getAttribute("idUsuario").toString())).get();
        model.addAttribute("cart", detalles);
        model.addAttribute("orden",orden);
        model.addAttribute("usuario",usuario);
        return "usuario/resumenorden";
    }

    @GetMapping("/saveOrder")
    public String saveOrder(HttpSession session){
        Date fechaCreacion = new Date();
        orden.setFechaCreacion(fechaCreacion);
        orden.setNumeroOrden(ordenService.generateNumeroOrden());

        //Usuario que hace referencia a la orden
        Usuario usuario = usuarioService.findById(Integer.parseInt(session.getAttribute("idUsuario").toString())).get();
        orden.setUsuario(usuario);

        ordenService.save(orden);

        //Guardar Detalles
        for(DetalleOrden dt : detalles){
            dt.setOrden(orden);
            detalleService.save(dt);
        }

        //Limpiear los valores que tienen los detalles
        orden = new Orden();
        detalles.clear();
        return "redirect:/";
    }

    @PostMapping("/search")
    public String searchProduct(@RequestParam String nombre, Model model){
        log.info("Nombre del producto {}",nombre);
        List<Producto> productos = sProducto.finAll().stream().filter(p -> p.getNombre().contains(nombre)).collect(Collectors.toList());
        model.addAttribute("productos",productos);
        return "usuario/home";
    }

}
