
package com.example.demo.dto;

import java.time.LocalDateTime;

import com.example.demo.models.EstadoTicket;
import com.example.demo.models.PrioridadTicket;
import com.example.demo.models.TipoTicket;

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
public class TicketDto {
    private String id;

    @NotBlank(message = "El título es obligatorio")
    private String tit;

    @NotBlank(message = "La descripción es obligatoria")
    private String descrip;

    @NotNull(message = "El tipo es obligatorio")
    private TipoTicket tip;

    private EstadoTicket est;

    @NotNull(message = "La prioridad es obligatoria")
    private PrioridadTicket priori;

    private String activoId;
    private String solicitanteId;
    private String asignadoId;

    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;
    private LocalDateTime fechaCierre;

    private String comentario;
    private String archivoNombre;
}
