package com.example.demo.models;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "mantenimiento")
@Data
@AllArgsConstructor 
@NoArgsConstructor 
@Builder
public class OrdenMantenimiento {
    @Id
    private String id;
    private String descripcion;
    private EstadoOrden estado;
    private String activoId;
    private String activoNombre;
    private String activoInfo;
    private String tecnicoId;
    private String tecnicoNombre;
    private TipoMantenimiento tipo;
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
