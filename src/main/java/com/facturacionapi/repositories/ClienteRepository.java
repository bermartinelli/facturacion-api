package com.facturacionapi.repositories;

import com.facturacionapi.entities.Cliente;
import com.facturacionapi.entities.Region;
//import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

@Query("SELECT r FROM Region r ORDER BY r.nombre")
    public List<Region> findAllRegiones();
}
