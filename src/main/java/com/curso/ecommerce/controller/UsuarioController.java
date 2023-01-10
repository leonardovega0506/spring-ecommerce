package com.curso.ecommerce.controller;

import com.curso.ecommerce.model.Usuario;
import com.curso.ecommerce.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    private final Logger logger = LoggerFactory.getLogger(UsuarioController.class);

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/registro")
    public String create(){
        return "usuario/registro";
    }

    @PostMapping("/save")
    public String save(Usuario usuario){
        logger.info("Usuario Registro: {}",usuario);
        usuario.setTipo("USER");
        usuarioService.save(usuario);
        return "redirect:/";
    }
    @GetMapping("/login")
    public String login(){
        return "usuario/login";
    }
    @PostMapping("/acceder")
    public String acceder(Usuario usuario, HttpSession httpSession) {
        logger.info("ACCESOS : {}", usuario);
        Optional<Usuario> user = usuarioService.findByEmail(usuario.getEmail());
        //logger.info("USUARIO DE DB : {}",user);

        if (user.isPresent()) {
            httpSession.setAttribute("idUsuario", user.get().getIdUsuario());
            if (user.get().getTipo().equals("ADMIN")) {
                return "redirect:/administrador";
            } else {
                return "redirect:/";
            }
        }
        else{
            logger.info("USUARIO NO EXITENTE");
        }
    return "redirect:/";
    }
}
