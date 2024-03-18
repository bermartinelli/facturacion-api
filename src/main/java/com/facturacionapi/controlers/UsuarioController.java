package com.facturacionapi.controlers;


import com.facturacionapi.DTO.ProductoDTO;
import com.facturacionapi.DTO.UsuarioDTO;
import com.facturacionapi.entities.Producto;
import com.facturacionapi.entities.Usuario;
import com.facturacionapi.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = {"http://localhost:4200", "*"})
@RestController
@RequestMapping("/api")
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;

    @Secured({"ROLE_ADMIN"})
    @GetMapping("/usuarios")
    public ResponseEntity<?> findAll(){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(usuarioService.findAll());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("NO ENCONTRADO");
        }
    }

    @Secured({"ROLE_ADMIN"})
    @GetMapping("/usuarios/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {

        Usuario usuario = null;
        Map<String, Object> response = new HashMap<>();
        try{
            usuario = usuarioService.getEntityById(id);
        } catch (DataAccessException e) {
            response.put("mensaje", "error al realizar la consulta en la BD");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (usuario ==null) {
            response.put("mensaje", "El usuario con ID: ".concat(id.toString().concat(" no existe en la BD")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<UsuarioDTO>(usuarioService.getById(id), HttpStatus.OK);
    }

    @Secured({"ROLE_ADMIN"})
    @PostMapping("/usuarios")
    public ResponseEntity<?> save(@Valid @RequestBody UsuarioDTO request, BindingResult result){
        UsuarioDTO dto = null;
        Map<String, Object> response = new HashMap<>();

        if(result.hasErrors()){
            List<String> errors = result.getFieldErrors().stream().map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage()).collect(Collectors.toList());

            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        try {
            //dto = usuarioService.save(request);
        } catch (DataAccessException e){
            response.put("mensaje","Error al realizar el insert desde la BD");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "El usuario fue creado con exito");
        response.put("usuario", dto);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }
}
