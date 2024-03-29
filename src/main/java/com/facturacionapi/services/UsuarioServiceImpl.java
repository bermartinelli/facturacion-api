package com.facturacionapi.services;

import com.facturacionapi.DTO.ProductoDTO;
import com.facturacionapi.DTO.UsuarioDTO;
import com.facturacionapi.entities.Producto;
import com.facturacionapi.entities.Usuario;
import com.facturacionapi.repositories.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioServiceImpl implements UsuarioService, UserDetailsService {

    private Logger logger = LoggerFactory.getLogger(UsuarioService.class);

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Override
    @Transactional(readOnly=true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Usuario usuario = usuarioRepository.findByUsername(username);

        if(usuario == null) {
            logger.error("Error en el login: no existe el usuario '"+username+"' en el sistema!");
            throw new UsernameNotFoundException("Error en el login: no existe el usuario '"+username+"' en el sistema!");
        }

        List<GrantedAuthority> authorities = usuario.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getNombre()))
                .peek(authority -> logger.info("Role: " + authority.getAuthority()))
                .collect(Collectors.toList());

        return new User(usuario.getUsername(), usuario.getPassword(), usuario.getEnabled(), true, true, true, authorities);
    }

    @Override
    @Transactional(readOnly=true)
    public Usuario findByUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }

    @Transactional
    public List<UsuarioDTO> findAll() throws Exception{
        var resultado = this.usuarioRepository.findAll();
        try{
            return resultado.stream().map(UsuarioDTO::new).collect(Collectors.toList());
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public UsuarioDTO getById(Long id){
        var resultado = this.usuarioRepository.findById(id);
        if(!resultado.isPresent()) return null;
            return new UsuarioDTO(resultado.get());
    }

    @Override
    @Transactional
    public Usuario getEntityById(Long id){
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        if(usuario.isPresent()) {
            Usuario user= usuario.get();
            return user;
        } else return null;
    }




}
