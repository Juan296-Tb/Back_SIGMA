package com.example.demo.models;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Document(collection = "ticket")
@Data 
@AllArgsConstructor 
@NoArgsConstructor 
@Builder
public class Ticket {
    @Id
    private String id;

    private String numero;
    private String titulo;
    private String descripcion;
    private TipoTicket tipo;
    private EstadoTicket estado;
    private PrioridadTicket prioridad;
    private String activoId;
    private String activo;
    private String solicitanteId;
    private String responsable;
    private String asignadoId;
    private String tecnicoNombre;
    private String ubicacion;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;
    private LocalDateTime fechaCierre;
    private String comentario;
    private String archivoNombre;
    private String archivoUrl;
}
