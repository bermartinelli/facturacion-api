package com.facturacionapi.repositories;

import com.facturacionapi.entities.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {


    public List<Producto> findByNombreContainingIgnoreCase(String name);

    public List<Producto> findByNombreStartingWithIgnoreCase(String name);

    public List<Producto> findByNombreIgnoreCase(String name);

}
