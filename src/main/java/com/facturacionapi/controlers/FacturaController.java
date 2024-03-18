package com.facturacionapi.controlers;

import com.facturacionapi.DTO.FacturaDTO;
import com.facturacionapi.services.FacturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = {"http://localhost:4200", "*"})
@RestController
@RequestMapping("/api")
public class FacturaController {

    @Autowired
    private FacturaService facturaService;

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping("/facturas/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        return new ResponseEntity<FacturaDTO>(facturaService.getById(id), HttpStatus.OK);
    }

    @Secured({"ROLE_ADMIN"})
    @DeleteMapping("/facturas/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id){
        facturaService.deleteById(id);
    }

    @Secured({"ROLE_ADMIN"})
    @GetMapping("facturas/filtrarProductos/{term}")
    public ResponseEntity<?> filtrarProductos(@PathVariable String term){
        return new ResponseEntity<>(facturaService.getProductoByNombre(term), HttpStatus.OK);
    }

    @Secured({"ROLE_ADMIN"})
    @PostMapping("/facturas")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> save(@RequestBody FacturaDTO request){
       FacturaDTO dto = null;
        Map<String, Object> response = new HashMap<>();

        try {
            dto = facturaService.save(request);
            //return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.save(request));
        } catch (DataAccessException e){
            response.put("mensaje","Error al realizar el insert desde la BD");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "La factura fue creado con exito!");
        response.put("factura", dto);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);

    }


    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping("/facturas/page/{page}")
    public ResponseEntity<?> findAll(@PathVariable Integer page){
        try {
            Pageable pageable = PageRequest.of(page, 10);
            return ResponseEntity.status(HttpStatus.OK).body(facturaService.findAll(pageable));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("NO ENCONTRADo");
        }
    }


}
