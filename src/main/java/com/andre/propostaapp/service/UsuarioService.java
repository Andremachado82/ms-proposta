package com.andre.propostaapp.service;

import com.andre.propostaapp.entity.Usuario;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    public Usuario getAll() {
        Usuario usuario = new Usuario();
        return usuario;
    }
}
