package com.example.demo.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.TicketDto;
import com.example.demo.models.Ticket;
import com.example.demo.repositories.TicketRepository;
import com.example.demo.repositories.ActivoRepository;
import com.example.demo.repositories.UsuarioRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/tickets")
@Validated
public class TicketController {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private ActivoRepository activoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    // ======================
    // CREAR
    // ======================
    @PostMapping
    public ResponseEntity<TicketDto> crear(@Valid @RequestBody TicketDto dto) {

        Ticket ticket = mapToEntity(dto);

        ticket.setFechaCreacion(LocalDateTime.now());
        ticket.setFechaActualizacion(LocalDateTime.now());

        Ticket guardado = ticketRepository.save(ticket);

        return ResponseEntity.ok(mapToDto(guardado));
    }

    // ======================
    // LISTAR
    // ======================
    @GetMapping
    public List<TicketDto> listar() {
        return ticketRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    // ======================
    // POR ID
    // ======================
    @GetMapping("/{id}")
    public ResponseEntity<TicketDto> obtenerPorId(@PathVariable String id) {
        return ticketRepository.findById(id)
                .map(ticket -> ResponseEntity.ok(mapToDto(ticket)))
                .orElse(ResponseEntity.notFound().build());
    }

    // ======================
    // ACTUALIZAR
    // ======================
    @PutMapping("/{id}")
    public ResponseEntity<TicketDto> actualizar(
            @PathVariable String id,
            @Valid @RequestBody TicketDto dto) {

        return ticketRepository.findById(id)
                .map(ticket -> {

                    ticket.setTitulo(dto.getTit());
                    ticket.setDescripcion(dto.getDescrip());
                    ticket.setTipo(dto.getTip());
                    ticket.setEstado(dto.getEst());
                    ticket.setPrioridad(dto.getPriori());
                    ticket.setActivoId(dto.getActivoId());
                    ticket.setSolicitanteId(dto.getSolicitanteId());
                    ticket.setAsignadoId(dto.getAsignadoId());
                    ticket.setComentario(dto.getComentario());
                    ticket.setArchivoNombre(dto.getArchivoNombre());
                    ticket.setFechaActualizacion(LocalDateTime.now());

                    Ticket actualizado = ticketRepository.save(ticket);

                    return ResponseEntity.ok(mapToDto(actualizado));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // ======================
    // ELIMINAR
    // ======================
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable String id) {

        if (!ticketRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        ticketRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // ======================
    // MAPPERS
    // ======================
    private Ticket mapToEntity(TicketDto dto) {
        return Ticket.builder()
                .id(dto.getId())
                .titulo(dto.getTit())
                .descripcion(dto.getDescrip())
                .tipo(dto.getTip())
                .estado(dto.getEst())
                .prioridad(dto.getPriori())
                .activoId(dto.getActivoId())
                .solicitanteId(dto.getSolicitanteId())
                .asignadoId(dto.getAsignadoId())
                .fechaCreacion(dto.getFechaCreacion())
                .fechaActualizacion(dto.getFechaActualizacion())
                .fechaCierre(dto.getFechaCierre())
                .comentario(dto.getComentario())
                .archivoNombre(dto.getArchivoNombre())
                .build();
    }

    private TicketDto mapToDto(Ticket ticket) {

        String activoNombre = activoRepository.findById(ticket.getActivoId())
                .map(a -> a.getTitulo())
                .orElse("—");

        String responsableNombre = usuarioRepository.findById(ticket.getAsignadoId())
                .map(u -> u.getNom())
                .orElse("—");

        return TicketDto.builder()
                .id(ticket.getId())
                .tit(ticket.getTitulo())
                .descrip(ticket.getDescripcion())
                .tip(ticket.getTipo())
                .est(ticket.getEstado())
                .priori(ticket.getPrioridad())
                .activoId(ticket.getActivoId())
                .solicitanteId(ticket.getSolicitanteId())
                .asignadoId(ticket.getAsignadoId())

                // 🔥 NUEVO
                .activoNombre(activoNombre)
                .responsableNombre(responsableNombre)

                .fechaCreacion(ticket.getFechaCreacion())
                .fechaActualizacion(ticket.getFechaActualizacion())
                .fechaCierre(ticket.getFechaCierre())
                .comentario(ticket.getComentario())
                .archivoNombre(ticket.getArchivoNombre())
                .build();
    }
}