package com.example.demo.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.demo.dto.OrdenMantenimientoDto;
import com.example.demo.mapper.OrdenMantenimientoMapper;
import com.example.demo.models.EstadoOrden;
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

        // Generar ordenId automático si no viene del frontend
        if (entity.getOrdenId() == null || entity.getOrdenId().isBlank()) {
            String numero = String.format("%03d", (int)(Math.random() * 900) + 100);
            entity.setOrdenId("OT-" + numero);
        }

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

        existente.setOrdenId(dto.getOrdenId());
        existente.setDescripcion(dto.getDescripcion());
        existente.setEstado(dto.getEstado());
        existente.setPrioridad(dto.getPrioridad());
        existente.setTipo(dto.getTipo());
        existente.setActivoId(dto.getActivoId());
        existente.setActivoNombre(dto.getActivoNombre());
        existente.setActivoInfo(dto.getActivoInfo());
        existente.setUbicacion(dto.getUbicacion());
        existente.setTecnicoId(dto.getTecnicoId());
        existente.setTecnicoNombre(dto.getTecnicoNombre());
        existente.setTicketId(dto.getTicketId());
        existente.setOrigen(dto.getOrigen());
        existente.setOrigenId(dto.getOrigenId());
        existente.setVentana(dto.getVentana());
        existente.setVentanaSub(dto.getVentanaSub());
        existente.setProgreso(dto.getProgreso());
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

    public List<OrdenMantenimientoDto> filtrar(String estado, String prioridad) {
        return repository.findAll().stream()
            .filter(o -> estado == null || o.getEstado().name().equalsIgnoreCase(estado))
            .filter(o -> prioridad == null || o.getPrioridad().name().equalsIgnoreCase(prioridad))
            .map(mapper::toDto)
            .collect(Collectors.toList());
    }

    public List<OrdenMantenimientoDto> listarPorTecnico(String tecnicoId) {
        return repository.findByTecnicoId(tecnicoId).stream()
            .map(mapper::toDto)
            .collect(Collectors.toList());
    }

    public OrdenMantenimientoDto cambiarEstado(String id, String nuevoEstado) {
        OrdenMantenimiento orden = repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Orden no encontrada"));
        orden.setEstado(EstadoOrden.valueOf(nuevoEstado));
        return mapper.toDto(repository.save(orden));
    }
}
