package com.example.demo.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.demo.dto.OrdenMantenimientoDto;
import com.example.demo.mapper.OrdenMantenimientoMapper;
import com.example.demo.models.OrdenMantenimiento;
import com.example.demo.repositories.OrdenMantenimientoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrdenMantenimientoService {

    private final OrdenMantenimientoRepository repository;
    private final OrdenMantenimientoMapper mapper;

    public OrdenMantenimientoDto crear(OrdenMantenimientoDto dto) {
        OrdenMantenimiento entity = mapper.toEntity(dto);
        return mapper.toDto(repository.save(entity));
    }

    public List<OrdenMantenimientoDto> listar() {
        return repository.findAll()
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    public OrdenMantenimientoDto obtenerPorId(String id) {
        OrdenMantenimiento entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Orden no encontrada"));
        return mapper.toDto(entity);
    }

    public OrdenMantenimientoDto actualizar(String id, OrdenMantenimientoDto dto) {
        OrdenMantenimiento existente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Orden no encontrada"));

        // Actualización campo por campo
        existente.setDescripcion(dto.getDescripcion());
        existente.setEstado(dto.getEstado());
        existente.setActivoId(dto.getActivoId());
        existente.setActivoInfo(dto.getActivoInfo());
        existente.setTecnicoId(dto.getTecnicoId());
        existente.setTecnicoNombre(dto.getTecnicoNombre());
        existente.setTipo(dto.getTipo());
        existente.setTicketId(dto.getTicketId());
        existente.setPrioridad(dto.getPrioridad());
        existente.setVentana(dto.getVentana());
        existente.setVentanaSub(dto.getVentanaSub());
        existente.setFechaProgramada(dto.getFechaProgramada());
        existente.setFechaInicio(dto.getFechaInicio());
        existente.setFechaFin(dto.getFechaFin());
        existente.setObservaciones(dto.getObservaciones());
        existente.setCosto(dto.getCosto());

        return mapper.toDto(repository.save(existente));
    }

    public void eliminar(String id) {
        repository.deleteById(id);
    }
}
