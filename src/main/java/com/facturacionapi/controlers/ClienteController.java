package com.facturacionapi.controlers;

import com.facturacionapi.DTO.ClienteDTO;
import com.facturacionapi.entities.Cliente;
import com.facturacionapi.services.ClienteService;
import com.facturacionapi.services.UploadFileService;
//import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.*;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private UploadFileService uploadService;

    //private Long id;

    @GetMapping("/clientes")
    public ResponseEntity<?> findAll(){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(clienteService.findAll());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("NO ENCONTRADo");
        }
    }

    @GetMapping("/clientes/page/{page}")
    public ResponseEntity<?> findAll(@PathVariable Integer page){
        try {
            Pageable pageable = PageRequest.of(page, 10);
            return ResponseEntity.status(HttpStatus.OK).body(clienteService.findAll(pageable));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("NO ENCONTRADo");
        }
    }
   /* @GetMapping("/clientes/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(clienteService.getById(id));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"message\": \"ID no encontrado\"}");
        }
    }
*/
    //otra manera de manejar errores en el get por ID:

   @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping("/clientes/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {

        Cliente cliente = null;
        Map<String, Object> response = new HashMap<>();
        try{
            cliente = clienteService.getEntityById(id);
        } catch (DataAccessException e) {
            response.put("mensaje", "error al realizar la consulta en la BD");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (cliente ==null) {
            response.put("mensaje", "El cliente con ID: ".concat(id.toString().concat(" no existe en la BD")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<ClienteDTO>(clienteService.getById(id), HttpStatus.OK);
    }


    @Secured({"ROLE_ADMIN"})
    @DeleteMapping("/clientes/{id}")
    public ResponseEntity<?> deleteCliente(@PathVariable Long id){

        Map<String, Object> response = new HashMap<>();

        if(clienteService.getEntityById(id) == null) {
            response.put("mensaje", "No se pudo eliminar, el cliente con ID: ".concat(id.toString().concat(" no existe en la BD")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        try {

            Cliente cliente = clienteService.getEntityById(id);
            String nombreAnterior = cliente.getFoto();
            if(nombreAnterior != null && nombreAnterior.length() > 0) {
                Path rutaArchivoAnterior = Paths.get("uploads").resolve(nombreAnterior).toAbsolutePath();
                File archivoFotoAnterior = rutaArchivoAnterior.toFile();
                if(archivoFotoAnterior.exists() && archivoFotoAnterior.canRead()) {
                    archivoFotoAnterior.delete();
                }
            }

            clienteService.deleteCliente(id);

            //return ResponseEntity.status(HttpStatus.OK).body("{\"message\": \"Se elimino correctamente\"}");
        } catch (EmptyResultDataAccessException e){
            response.put("mensaje", "error al eliminar el cliente en la BD");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            //return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"message\": \"No pudo ser eliminado\"}");
        }

        response.put("mensaje", "El cliente fue eliminado con éxito");
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

    }

    @Secured({"ROLE_ADMIN"})
    @PostMapping("/clientes")
    public ResponseEntity<?> save(@Valid @RequestBody ClienteDTO request, BindingResult result){
        ClienteDTO dto = null;
        Map<String, Object> response = new HashMap<>();

        if(result.hasErrors()){
            List<String> errors = result.getFieldErrors().stream().map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage()).collect(Collectors.toList());

            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        try {
            dto = clienteService.save(request);
            //return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.save(request));
        } catch (DataAccessException e){
            response.put("mensaje","Error al realizar el insert desde la BD");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "El cliente fue creado con exito!");
        response.put("cliente", dto);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @Secured({"ROLE_ADMIN"})
    @PutMapping("/clientes/{id}")
    public ResponseEntity<?> update(@RequestBody ClienteDTO cliente, BindingResult result,@PathVariable Long id){
        ClienteDTO dto = null;
        Map<String, Object> response = new HashMap<>();

        if(result.hasErrors()){
            List<String> errors = result.getFieldErrors().stream().map(err -> "El campo " + err.getField() + " " + err.getDefaultMessage()).collect(Collectors.toList());

            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        if(clienteService.getEntityById(id) == null) {
            response.put("mensaje", "No se pudo editar, el cliente con ID: ".concat(id.toString().concat(" no existe en la BD")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        try {
            dto = clienteService.update(id, cliente);
            //return ResponseEntity.status(HttpStatus.OK).body(clienteService.update(id, cliente));

            //NO PUEDO HACER QUE ENTRE DENTRO DE ESTE CATCH
        } catch (DataAccessException e){
            response.put("mensaje","Error al actualizar el cliente desde la BD");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "El cliente fue actualizado con exito!");
        response.put("cliente", dto);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @PostMapping("/clientes/upload")
    public ResponseEntity<?> upload(@RequestParam("archivo") MultipartFile archivo, @RequestParam("id") Long id){
        Map<String, Object> response = new HashMap<>();
        Cliente cliente = clienteService.getEntityById(id);

        if(!archivo.isEmpty()) {

            String fotoActual = null;
            try {
                fotoActual = uploadService.copiar(archivo);
            } catch(IOException e){
                response.put("mensaje", "Error al subir la imagen del cliente");
                response.put("error", e.getMessage().concat(": ").concat(e.getCause().getMessage()));
            }

            String nombreFotoAnterior = cliente.getFoto();

            uploadService.eliminar(nombreFotoAnterior);

            cliente.setFoto(fotoActual);
            clienteService.saveEntity(cliente);

            response.put("cliente", cliente);
            response.put("mensaje", "La foto se subio correctamente: " + fotoActual);

        }
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    //@Secured({"ROLE_ADMIN","ROLE_USER"})
    @GetMapping("/upload/{nombreFoto:.+}") //los :.+ indica que le sigue la extension del archivo.
    public ResponseEntity<Resource> verFoto(@PathVariable String nombreFoto) {


        Resource recurso = null;

        try{
            recurso = uploadService.cargar(nombreFoto);
        } catch(MalformedURLException e){
            e.printStackTrace();
        }


        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() + "\""); //fuerza en la resp que se descargue la imagen
        return new ResponseEntity<Resource>( recurso, header, HttpStatus.OK);
    }

    @Secured({"ROLE_ADMIN"})
    @GetMapping("/clientes/regiones")
    public ResponseEntity<?> findAllRegiones(){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(clienteService.getAllRegiones());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("NO ENCONTRADo");
        }
    }

}
