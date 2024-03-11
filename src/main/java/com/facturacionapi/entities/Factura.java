package com.facturacionapi.entities;

import com.facturacionapi.DTO.FacturaDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name="facturas")
public class Factura implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String descripcion;
    private String observacion;

    @Column(name = "create_at")
    @Temporal(TemporalType.DATE)
    private Date createAt;

    @JsonIgnoreProperties({"facturas", "hibernateLazyInitializer", "handler"}) //hay que ignorar la contraparte de la relacion.
    @ManyToOne(fetch = FetchType.LAZY)
    //relacion bidireccional. Ambos saben que el otro existe y se puede a uno desde el otro.
    @JoinColumn(name = "cliente_id") //si no lo pongo me crea la columna cliente_cliente_id en facturas.
    private Cliente cliente;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL) //si elimino una factura, elimino todos sus items.
    @JoinColumn(name = "factura_id") //es necesario para identificar el nombre de la llave foranea. La llave foranea es el mismo nombre que la columna en la BD.
    //relacion unidireccional. El item no sabe de la exisitencia de la factura. Solo puedo acceder al itemfactura desde aca. No puedo
    //acceder a la factura desde el item.
    private List<ItemFactura> items;


    @PrePersist
    public void prePersist(){
        this.createAt = new Date();
    }

    public Factura(){
        items = new ArrayList<ItemFactura>();
    }

    public Factura(FacturaDTO dto){
        this.id = dto.getId();
        this.descripcion = dto.getDescripcion();
        this.observacion = dto.getObservacion();
        this.createAt = dto.getCreateAt();
        this.cliente = dto.getCliente();
        this.items = dto.getItems();
    }

    public double getTotal(){
        Double total = 0.00;
        for (ItemFactura item: items){
            total += item.getImporte();
        }
        return total;
    }
}
