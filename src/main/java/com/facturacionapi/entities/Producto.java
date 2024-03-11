package com.facturacionapi.entities;

import com.facturacionapi.DTO.ProductoDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "productos")
public class Producto implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private Double precio;

    @Column(name = "create_at")
    @Temporal(TemporalType.DATE)
    private Date createAt;

    private Integer stock;

    public Producto(ProductoDTO dto){
        this.id = dto.getId();
        this.nombre = dto.getNombre();
        this.precio = dto.getPrecio();
        this.createAt = dto.getCreateAt();
        this.stock = dto.getStock();
    }

    @PrePersist
    public void prePersist(){
        this.createAt = new Date();
    }

}
