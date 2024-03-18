package com.facturacionapi.services;

import com.facturacionapi.DTO.UsuarioDTO;
import com.facturacionapi.entities.Usuario;

import java.util.List;


public interface UsuarioService {
    public Usuario findByUsername(String username);

    public List<UsuarioDTO> findAll() throws Exception;

    public Usuario getEntityById(Long id);

    public UsuarioDTO getById(Long id);



}

