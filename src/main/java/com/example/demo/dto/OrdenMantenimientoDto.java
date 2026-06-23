package com.example.demo.dto;

import java.time.LocalDateTime;

import com.example.demo.models.EstadoOrden;
import com.example.demo.models.PrioridadTicket;
import com.example.demo.models.TipoMantenimiento;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrdenMantenimientoDto {
    private String id;
    private String ordenId; 

    private String descripcion;

    @JsonSetter(nulls = Nulls.SKIP)
    private EstadoOrden estado;

    @JsonSetter(nulls = Nulls.SKIP)
    private TipoMantenimiento tipo;

    @NotBlank(message = "El activo es obligatorio")
    private String activoId;
    private String activoNombre;
    private String activoInfo;
    private String ubicacion;

    @NotBlank(message = "El técnico es obligatorio")
    private String tecnicoId;

    private String tecnicoNombre;

    private String ticketId;
    private String origen;
    private String origenId;
    @JsonSetter(nulls = Nulls.SKIP)
    private PrioridadTicket prioridad;

    private String ventana;
    private String ventanaSub;
    private int progreso;

    private LocalDateTime fechaProgramada;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private String observaciones;
    private Double costo;
}
