package com.facturacionapi.DTO;

import com.facturacionapi.entities.Producto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ProductoDTO {

    private Long id;
    private String nombre;
    private Double precio;
    private Date createAt;
    private Integer stock;

    public ProductoDTO(Producto producto){
        this.id = producto.getId();
        this.nombre = producto.getNombre();
        this.precio = producto.getPrecio();
        this.createAt = producto.getCreateAt();
        this.stock = producto.getStock();
    }
}
