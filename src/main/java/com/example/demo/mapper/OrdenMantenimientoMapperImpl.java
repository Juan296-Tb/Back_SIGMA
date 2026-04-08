package com.example.demo.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.demo.dto.OrdenMantenimientoDto;
import com.example.demo.models.OrdenMantenimiento;

@Component
public class OrdenMantenimientoMapperImpl implements OrdenMantenimientoMapper {

    @Override
    public OrdenMantenimiento toEntity(OrdenMantenimientoDto dto) {
        if (dto == null)
            return null;

        return OrdenMantenimiento.builder()
                .id(dto.getId())
                .descripcion(dto.getDescripcion())
                .estado(dto.getEstado())
                .activoId(dto.getActivoId())
                .activoInfo(dto.getActivoInfo())
                .tecnicoId(dto.getTecnicoId())
                .tecnicoNombre(dto.getTecnicoNombre())
                .tipo(dto.getTipo())
                .ticketId(dto.getTicketId())
                .prioridad(dto.getPrioridad())
                .ventana(dto.getVentana())
                .ventanaSub(dto.getVentanaSub())
                .fechaProgramada(dto.getFechaProgramada())
                .fechaInicio(dto.getFechaInicio())
                .fechaFin(dto.getFechaFin())
                .observaciones(dto.getObservaciones())
                .costo(dto.getCosto())
                .build();

        /**
         * Convierte un DTO en una entidad OrdenMantenimiento.
         * Usamos Builder para mayor claridad y evitar constructores largos.
         */
    }

    @Override
    public OrdenMantenimientoDto toDto(OrdenMantenimiento entity) {
        if (entity == null)
            return null;

        return OrdenMantenimientoDto.builder()
                .id(entity.getId())
                .descripcion(entity.getDescripcion())
                .estado(entity.getEstado())
                .activoId(entity.getActivoId())
                .activoInfo(entity.getActivoInfo())
                .tecnicoId(entity.getTecnicoId())
                .tecnicoNombre(entity.getTecnicoNombre())
                .tipo(entity.getTipo())
                .ticketId(entity.getTicketId())
                .prioridad(entity.getPrioridad())
                .ventana(entity.getVentana())
                .ventanaSub(entity.getVentanaSub())
                .fechaProgramada(entity.getFechaProgramada())
                .fechaInicio(entity.getFechaInicio())
                .fechaFin(entity.getFechaFin())
                .observaciones(entity.getObservaciones())
                .costo(entity.getCosto())
                .build();

        /**
         * Convierte una entidad OrdenMantenimiento en DTO.
         */
    }

    @Override
    public List<OrdenMantenimientoDto> toDtoList(List<OrdenMantenimiento> entities) {
        if (entities == null)
            return null;

        return entities.stream()
                .map(this::toDto)
                .toList();

        /**
         * Convierte lista de entidades a lista de DTOs usando streams.
         */
    }

    @Override
    public void updateEntity(OrdenMantenimientoDto dto, OrdenMantenimiento entity) {
        if (entity == null) {
            throw new IllegalArgumentException("La entidad OrdenMantenimiento no puede ser nula");
        }
        if (dto == null) {
            throw new IllegalArgumentException("El DTO OrdenMantenimientoDto no puede ser nulo");
        }

        entity.setDescripcion(dto.getDescripcion());
        entity.setEstado(dto.getEstado());
        entity.setActivoId(dto.getActivoId());
        entity.setActivoInfo(dto.getActivoInfo());
        entity.setTecnicoId(dto.getTecnicoId());
        entity.setTecnicoNombre(dto.getTecnicoNombre());
        entity.setTipo(dto.getTipo());
        entity.setTicketId(dto.getTicketId());
        entity.setPrioridad(dto.getPrioridad());
        entity.setVentana(dto.getVentana());
        entity.setVentanaSub(dto.getVentanaSub());
        entity.setFechaProgramada(dto.getFechaProgramada());
        entity.setFechaInicio(dto.getFechaInicio());
        entity.setFechaFin(dto.getFechaFin());
        entity.setObservaciones(dto.getObservaciones());
        entity.setCosto(dto.getCosto());

        /**
         * Actualiza una entidad existente con los datos del DTO.
         * Aquí usamos setters porque el objeto ya existe.
         */
    }
}
