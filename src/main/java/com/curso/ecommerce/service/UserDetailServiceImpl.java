package com.curso.ecommerce.service;

import com.curso.ecommerce.model.Usuario;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    HttpSession session;

    private Logger log = LoggerFactory.getLogger(UserDetailServiceImpl.class);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Esto es el username");
        Optional<Usuario> oUsuario = usuarioService.findByEmail(username);

        if(oUsuario.isPresent()){
            log.info("ESTO ES EL ID DEL USUARIO: {}",oUsuario.get().getIdUsuario());
            session.setAttribute("idUsuario",oUsuario.get().getIdUsuario());
            Usuario usuario = oUsuario.get();
            return User.builder().username(usuario.getNombre()).password(passwordEncoder.encode(usuario.getPassword())).roles(usuario.getTipo()).build();
        }else {
           throw new UsernameNotFoundException("Usuario no encontrado");
        }

    }
}
