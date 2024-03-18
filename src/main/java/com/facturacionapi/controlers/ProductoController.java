package com.facturacionapi.controlers;


import com.facturacionapi.DTO.ProductoDTO;
import com.facturacionapi.entities.Producto;
import com.facturacionapi.services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping("/productos")
    public ResponseEntity<?> findAll(){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(productoService.findAll());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("NO ENCONTRADO");
        }
    }

    @GetMapping("/productos/page/{page}")
    public ResponseEntity<?> findAll(@PathVariable Integer page){
        try {
            Pageable pageable = PageRequest.of(page, 10);
            return ResponseEntity.status(HttpStatus.OK).body(productoService.findAll(pageable));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("NO ENCONTRADO");
        }
    }

    @GetMapping("/productos/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {

        Producto producto = null;
        Map<String, Object> response = new HashMap<>();
        try{
            producto = productoService.getEntityById(id);
        } catch (DataAccessException e) {
            response.put("mensaje", "error al realizar la consulta en la BD");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (producto ==null) {
            response.put("mensaje", "El cliente con ID: ".concat(id.toString().concat(" no existe en la BD")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<ProductoDTO>(productoService.getById(id), HttpStatus.OK);
    }

    @Secured({"ROLE_USER"})
    @PostMapping("/productos")
    public ResponseEntity<?> save(@Valid @RequestBody ProductoDTO request, BindingResult result){
        ProductoDTO dto = null;
        Map<String, Object> response = new HashMap<>();

        if(result.hasErrors()){
            List<String> errors = result.getFieldErrors().stream().map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage()).collect(Collectors.toList());

            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        try {
            dto = productoService.save(request);
            //return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.save(request));
        } catch (DataAccessException e){
            response.put("mensaje","Error al realizar el insert desde la BD");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "El producto fue creado con exito!");
        response.put("producto", dto);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @Secured({"ROLE_USER"})
    @PutMapping("/productos/{id}")
    public ResponseEntity<?> update(@RequestBody ProductoDTO producto, BindingResult result,@PathVariable Long id){
        ProductoDTO dto = null;
        Map<String, Object> response = new HashMap<>();

        if(result.hasErrors()){
            List<String> errors = result.getFieldErrors().stream().map(err -> "El campo " + err.getField() + " " + err.getDefaultMessage()).collect(Collectors.toList());

            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        if(productoService.getEntityById(id) == null) {
            response.put("mensaje", "No se pudo editar, el producto con ID: ".concat(id.toString().concat(" no existe en la BD")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        try {
            dto = productoService.update(id, producto);
            //return ResponseEntity.status(HttpStatus.OK).body(clienteService.update(id, cliente));

            //NO PUEDO HACER QUE ENTRE DENTRO DE ESTE CATCH
        } catch (DataAccessException e){
            response.put("mensaje","Error al actualizar el producto desde la BD");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "El producto fue actualizado con exito");
        response.put("producto", dto);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @Secured({"ROLE_ADMIN"})
    @DeleteMapping("/productos/{id}")
    public ResponseEntity<?> deleteProducto(@PathVariable Long id){

        Map<String, Object> response = new HashMap<>();

        if(productoService.getEntityById(id) == null) {
            response.put("mensaje", "No se pudo eliminar, el producto con ID: ".concat(id.toString().concat(" no existe en la BD")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        try {

            productoService.deleteProducto(id);

            //return ResponseEntity.status(HttpStatus.OK).body("{\"message\": \"Se elimino correctamente\"}");
        } catch (EmptyResultDataAccessException e){
            response.put("mensaje", "error al eliminar el producto en la BD");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            //return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"message\": \"No pudo ser eliminado\"}");
        }

        response.put("mensaje", "El producto fue eliminado con éxito");
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

    }

}
