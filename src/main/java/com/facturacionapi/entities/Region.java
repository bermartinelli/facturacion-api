package com.facturacionapi.entities;

//import jakarta.persistence.*;
import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name="regiones")
@Getter
@Setter
@NoArgsConstructor

public class Region implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;

    private static final long serialVersionUID = 1L;
}
