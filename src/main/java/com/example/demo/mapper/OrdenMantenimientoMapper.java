package com.example.demo.mapper;

import java.util.List;

import com.example.demo.dto.OrdenMantenimientoDto;
import com.example.demo.models.OrdenMantenimiento;

public interface OrdenMantenimientoMapper {

    OrdenMantenimiento toEntity(OrdenMantenimientoDto dto);

    OrdenMantenimientoDto toDto(OrdenMantenimiento entity);

    List<OrdenMantenimientoDto> toDtoList(List<OrdenMantenimiento> entities);

    void updateEntity(OrdenMantenimientoDto dto, OrdenMantenimiento entity);
}