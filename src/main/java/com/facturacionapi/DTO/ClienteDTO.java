package com.facturacionapi.DTO;

import com.facturacionapi.entities.Cliente;
import com.facturacionapi.entities.Factura;
import com.facturacionapi.entities.Region;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import java.util.Date;
import java.util.List;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ClienteDTO {
    private Long id;


    @NotBlank(message = "no puede estar vacio")
    @Size(min = 4, max = 15, message = "debe contener entre 4 y 15 caracteres")
    private String nombre;
    @NotBlank(message = "no puede estar vacio")
    private String apellido;
    @NotBlank(message = "no puede estar vacio")
    @Email(message = "debe ser de un formato válido")
    private String email;

    private Date createAt;


    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Region region;

    private String foto;

    private List<Factura> facturas;

    public ClienteDTO(){}

    public ClienteDTO(Cliente cliente){
        this.nombre = cliente.getNombre();
        this.apellido = cliente.getApellido();
        this.email = cliente.getEmail();
        this.id = cliente.getId();
        this.createAt = cliente.getCreateAt();
        this.foto = cliente.getFoto();
        this.region = cliente.getRegion();
        this.facturas = cliente.getFacturas();
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public List<Factura> getFacturas() {
        return facturas;
    }
}

