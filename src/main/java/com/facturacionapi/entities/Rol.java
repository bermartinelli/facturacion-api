package com.facturacionapi.entities;

import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="rol")
public class Rol implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, length = 20)
    private String nombre;

    //no tiene sentido poner aca el ManyTOMANu porque el rol no tiene porque ver a los usuarios.
    //Es relacion unidireccional pero la cardinalidad es muchos a muchos.

}
