package com.facturacionapi.DTO;

import com.facturacionapi.entities.Region;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class RegionDTO {

    private Long id;
    private String nombre;

    public RegionDTO(Region region){
        this.id= region.getId();
        this.nombre= region.getNombre();
    }

}
