package com.facturacionapi.services;

import com.facturacionapi.DTO.ProductoDTO;
import com.facturacionapi.entities.Producto;
import com.facturacionapi.repositories.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductoService {

    @Autowired
    ProductoRepository productoRepository;

    @Transactional
    public List<ProductoDTO> findAll() throws Exception{
        var resultado = this.productoRepository.findAll();
        try{
            return resultado.stream().map(ProductoDTO::new).collect(Collectors.toList());
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public Page<ProductoDTO> findAll(Pageable pageable) throws Exception{
        Page<Producto> resultado =  this.productoRepository.findAll(pageable);
        try{
            List<ProductoDTO> result = resultado.getContent().stream().map(ProductoDTO::new).collect(Collectors.toList());
            return new PageImpl<>(result, resultado.getPageable(), resultado.getTotalElements());
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Transactional
    public ProductoDTO save(ProductoDTO request) {
        Date fecha = new Date();
        request.setCreateAt(fecha);
        var producto = new Producto(request);
        var response = this.productoRepository.save(producto);
        return new ProductoDTO(response);
    }

    @Transactional
    public void deleteProducto(Long id) throws EmptyResultDataAccessException {
        try{
            this.productoRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e){
            throw e;
        }
    }

    @Transactional
    public ProductoDTO getById(Long id) {
        return new ProductoDTO(this.productoRepository.findById(id).orElse(null));
    }

    @Transactional
    public ProductoDTO update(Long id, ProductoDTO request) {
        try{
            var producto = this.productoRepository.findById(id).orElse(null);
            if (request.getNombre() != null) {
                producto.setNombre(request.getNombre());
            }
            if (request.getPrecio() != null) {
                producto.setPrecio(request.getPrecio());
            }
            if (request.getCreateAt() != null) {
                producto.setCreateAt(request.getCreateAt());
            }
            if (request.getStock() != null) {
                producto.setStock(request.getStock());
            }
            var response = this.productoRepository.save(producto);
            return new ProductoDTO(response);
        } catch (DataAccessException e){
            e.getMessage();
        }
        return null;
    }

    public Producto getEntityById(Long id) {
        Optional<Producto> producto = productoRepository.findById(id);
        if(producto.isPresent()) {
            Producto p= producto.get();
            return p;
        } else return null;    }
}
