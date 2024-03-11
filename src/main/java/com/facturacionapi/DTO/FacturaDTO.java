package com.facturacionapi.DTO;

import com.facturacionapi.entities.Cliente;
import com.facturacionapi.entities.Factura;
import com.facturacionapi.entities.ItemFactura;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class FacturaDTO {

    private Long id;
    private String descripcion;
    private String observacion;
    private Date createAt;

    @JsonIgnoreProperties({"facturas","hibernateLazyInitializer", "handler"})
    private Cliente cliente;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private List<ItemFactura> items;


    public FacturaDTO(Factura factura){
        this.id = factura.getId();
        this.descripcion = factura.getDescripcion();
        this.observacion = factura.getObservacion();
        this.createAt = factura.getCreateAt();
        this.cliente = factura.getCliente();
        this.items = factura.getItems();
    }

}
