package com.facturacionapi.DTO;

import com.facturacionapi.entities.ItemFactura;
import com.facturacionapi.entities.Producto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class ItemFacturaDTO {

    private Long id;
    private Integer cantidad;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Producto producto;

    public ItemFacturaDTO(ItemFactura item){
        this.id = item.getId();
        this.cantidad = item.getCantidad();
        this.producto = item.getProducto();
    }

}
