package com.andre.propostaapp.controller;

import com.andre.propostaapp.entity.Usuario;
import com.andre.propostaapp.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsuarioController {





    @Autowired
    UsuarioService usuarioService;
    @GetMapping(name = "/usuarios")
    public ResponseEntity<Usuario> getAll() {

        Usuario usuario = usuarioService.getAll();
        return ResponseEntity.ok().build();
    }
}
