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

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrdenMantenimientoDto {
    private String id;

    @NotBlank(message = "La descripción es obligatoria")
    private String descripcion;

    private EstadoOrden estado;

    @NotNull(message = "El tipo es obligatorio")
    private TipoMantenimiento tipo;

    @NotBlank(message = "El activo es obligatorio")
    private String activoId;

    private String activoInfo;

    @NotBlank(message = "El técnico es obligatorio")
    private String tecnicoId;

    private String tecnicoNombre;

    private String ticketId;
    private PrioridadTicket prioridad;

    private String ventana;
    private String ventanaSub;

    private LocalDateTime fechaProgramada;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private String observaciones;
    private Double costo;
}
