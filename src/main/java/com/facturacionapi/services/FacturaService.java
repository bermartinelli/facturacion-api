package com.facturacionapi.services;

import com.facturacionapi.DTO.FacturaDTO;
import com.facturacionapi.entities.Factura;
import com.facturacionapi.entities.Producto;
import com.facturacionapi.repositories.FacturaRepository;
import com.facturacionapi.repositories.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FacturaService {

    @Autowired
    FacturaRepository facturaRepository;

    @Autowired
    ProductoRepository productoRepository;


    @Transactional(readOnly = true)
    public FacturaDTO getById(Long id) {

        return new FacturaDTO(facturaRepository.findById(id).orElse(null));
    }

    public List<FacturaDTO> findAll() {
        return null;
    }

    @Transactional
    public FacturaDTO save(FacturaDTO request) {
        Date fecha = new Date();
        request.setCreateAt(fecha);
        var factura = new Factura(request);
        var response = facturaRepository.save(factura);
        return new FacturaDTO(response);
    }

    @Transactional
    public void deleteById(Long id) {

        facturaRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<Producto> getProductoByNombre(String string){
        return this.productoRepository.findByNombreContainingIgnoreCase(string);
    }

    public Page<FacturaDTO> findAll(Pageable pageable) throws Exception{
        Page<Factura> resultado =  this.facturaRepository.findAll(pageable);
        try{
            List<FacturaDTO> result = resultado.getContent().stream().map(FacturaDTO::new).collect(Collectors.toList());
            return new PageImpl<>(result, resultado.getPageable(), resultado.getTotalElements());
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

}
