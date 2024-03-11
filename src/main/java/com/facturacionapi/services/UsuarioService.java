package com.facturacionapi.services;

import com.facturacionapi.entities.Usuario;


public interface UsuarioService {
    public Usuario findByUsername(String username);
}
