package com.curso.ecommerce.controller;

import com.curso.ecommerce.model.Producto;
import com.curso.ecommerce.model.Usuario;
import com.curso.ecommerce.service.ProductoService;
import com.curso.ecommerce.service.UploadFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Controller
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private ProductoService sProducto;

    @Autowired
    private UploadFileService upload;
    private final Logger LOGGER = LoggerFactory.getLogger(ProductoController.class);
    @GetMapping("")
    public String show(Model model){
        model.addAttribute("productos",sProducto.finAll());
        return "productos/show";
    }

    @GetMapping("/create")
    public String create(){
        return "productos/create";
    }

    @PostMapping("/save")
    public String save(Producto producto,@RequestParam("img") MultipartFile file) throws IOException {
        LOGGER.info("ESTE ES EL OBJETO PRODUCTO {}",producto);
        Usuario u = new Usuario(1, "", "", "", "", "", "", "");
        producto.setUsuario(u);
        //imagen
        if(producto.getId() == null){ //Cuando se crea un producto
            String nombreImagen = upload.saveImage(file);
            producto.setImagen(nombreImagen);
        }
        else{
            if(file.isEmpty()){ //Editamos el producto pero no cambiamos la imagen
                Producto p = new Producto();
                p=sProducto.get(producto.getId()).get();
                producto.setImagen(p.getImagen());
            }else{
                String nombreImagen = upload.saveImage(file);
                producto.setImagen(nombreImagen);
            }
        }
        sProducto.save(producto);
        return "redirect:/productos";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable(value = "id") Integer id, Model model){
        Producto producto = new Producto();
        Optional<Producto> optionalProducto = sProducto.get(id);
        producto  = optionalProducto.get();

        LOGGER.info("Producto buscado {}",producto);
        model.addAttribute("producto",producto);
        return "productos/edit";
    }

    @PostMapping("/update")
    public String update(Producto producto){
        sProducto.update(producto);
        return "redirect:/productos";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable(value = "id") Integer id){
        sProducto.delete(id);
        return "redirect:/productos";
    }
}
