package com.facturacionapi.services;

import com.facturacionapi.DTO.ClienteDTO;
import com.facturacionapi.DTO.RegionDTO;
import com.facturacionapi.entities.Cliente;
import com.facturacionapi.entities.Region;
import com.facturacionapi.repositories.ClienteRepository;
//import jakarta.transaction.Transactional;
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
public class ClienteService {

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    ProductoRepository productoRepository;

    @Transactional
    public List<ClienteDTO> findAll() throws Exception{
        var resultado =  this.clienteRepository.findAll();
        try{
            return resultado.stream().map(ClienteDTO::new).collect(Collectors.toList());
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public Page<ClienteDTO> findAll(Pageable pageable) throws Exception{
        Page<Cliente> resultado =  this.clienteRepository.findAll(pageable);
        try{
            List<ClienteDTO> result = resultado.getContent().stream().map(ClienteDTO::new).collect(Collectors.toList());
            return new PageImpl<>(result, resultado.getPageable(), resultado.getTotalElements());
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Transactional
    public ClienteDTO save(ClienteDTO request) {
        Date fecha = new Date();
        request.setCreateAt(fecha);
        final var cliente = new Cliente(request);
        final var response = clienteRepository.save(cliente);
        return new ClienteDTO(response);
    }

    public Cliente saveEntity(Cliente cliente) {
        Date fecha = new Date();
        cliente.setCreateAt(fecha);
        final var response = clienteRepository.save(cliente);
        return response;
    }

    @Transactional
    public void deleteCliente(Long id) throws EmptyResultDataAccessException{
        try {
            clienteRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e){
             throw e;
        }
    }

    @Transactional
    public ClienteDTO getById(Long id) {
        var resultado = clienteRepository.findById(id);
                return new ClienteDTO(resultado.get());
    }

    public Cliente getEntityById(Long id){
        Optional<Cliente> cliente = clienteRepository.findById(id);
        if(cliente.isPresent()) {
            Cliente c = cliente.get();
            return c;
        } else return null;
    }

    @Transactional
    public ClienteDTO update(Long id, ClienteDTO dto){
        try {
            Optional<Cliente> cliente = clienteRepository.findById(id);
            if (cliente.isPresent()) {
                Cliente c = cliente.get();
                c.setNombre(dto.getNombre());
                c.setApellido(dto.getApellido());
                c.setEmail(dto.getEmail());
                Date fecha = new Date();
                c.setCreateAt(fecha);
                c.setRegion(dto.getRegion());
                Cliente response = clienteRepository.save(c);
                return new ClienteDTO(response);

            }
        } catch (DataAccessException e) {
              e.getMessage();
        }
        return null;
    }

    @Transactional
    public List<RegionDTO> getAllRegiones() throws Exception {
        List<Region> regiones = clienteRepository.findAllRegiones();
        try{
            return regiones.stream().map(RegionDTO::new).collect(Collectors.toList());
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }


}
