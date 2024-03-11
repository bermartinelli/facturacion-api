package com.facturacionapi.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "facturas_item")
public class ItemFactura implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer cantidad;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producto_id") //aca por mas q sea unidireccional, no hace falta el JoinColumn. Se crea automaticamnete.
    private Producto producto;


    //lo escribo como getImporte para que lo pueda incluir en JSON.
    public double getImporte(){
        return cantidad.doubleValue() * producto.getPrecio();
    }

}
