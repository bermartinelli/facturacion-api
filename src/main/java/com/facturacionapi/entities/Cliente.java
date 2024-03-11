package com.facturacionapi.entities;

import com.facturacionapi.DTO.ClienteDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


@Entity
@Table(name="clientes")
public class Cliente implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="cliente_id")
    private Long id;


   @Column (nullable = false)
   @NotBlank
   @Size(min = 4, max = 15)
    private String nombre;
    @Column (nullable = false)
    @NotBlank
    private String apellido;
    @Column (nullable = false, unique = true)
    @NotBlank
    @Email
    private String email;
    @Column(name="create_at")
    @NotNull
    @Temporal(TemporalType.DATE)
    private Date createAt;
    @Column
    private String foto;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="region_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    //para que no salga error supuestamente porq es LAZY.
    @NotNull
    private Region region;

    //el cliente lo ignoro para que no me sea recursiva la llamda en el JSON, y los otros dos para evitar datos basura devido al LAZY.
    @JsonIgnoreProperties({"cliente", "hibernateLazyInitializer", "handler"})
    //el mapped by marca el atributo de la contraparte de la relacion.
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cliente", cascade = CascadeType.ALL)
    //aca no va el JoinCollumn porque al marcar el mappedBy ya sabemos el nombre de la llave foranea.
    //la relacion es bidireccional, tengo un atributo cliente en la otra clase factura.
    private List<Factura> facturas;

    public Cliente(){}
    public Cliente(ClienteDTO dto){
        this.id = dto.getId();
        this.nombre = dto.getNombre();
        this.apellido = dto.getApellido();
        this.createAt = dto.getCreateAt();
        this.email = dto.getEmail();
        this.foto = dto.getFoto();
        this.region = dto.getRegion();
        this.facturas = dto.getFacturas();
    }


    public Cliente(String nombre, String apellido, String email, Date createAt, Region region) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.createAt = createAt;
        this.region = region;
    }

    public List<Factura> getFacturas() {
        return facturas;
    }

    public void setFacturas(List<Factura> facturas) {
        this.facturas = facturas;
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

    public String getNombre() {
        return nombre;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
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
}