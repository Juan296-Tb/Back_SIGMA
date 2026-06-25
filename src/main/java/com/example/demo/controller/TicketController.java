package com.example.demo.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.TicketDto;
import com.example.demo.models.EstadoTicket;
import com.example.demo.models.Ticket;
import com.example.demo.repositories.TicketRepository;
import com.example.demo.repositories.ActivoRepository;
import com.example.demo.repositories.UsuarioRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/tickets")
@Validated
@CrossOrigin(origins = "*")
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

    // Filtrar por estado y/o prioridad
    @GetMapping("/filtrar")
    public ResponseEntity<List<TicketDto>> filtrar(
            @RequestParam(required = false) String estado,
            @RequestParam(required = false) String prioridad) {

        List<TicketDto> resultado = ticketRepository.findAll()
                .stream()
                .filter(t -> estado == null || t.getEstado().name().equalsIgnoreCase(estado))
                .filter(t -> prioridad == null || t.getPrioridad().name().equalsIgnoreCase(prioridad))
                .map(this::mapToDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(resultado);
    }

    // Tickets asignados a un técnico — para el home del técnico
    @GetMapping("/tecnico/{tecnicoId}")
    public ResponseEntity<List<TicketDto>> porTecnico(@PathVariable String tecnicoId) {
        List<TicketDto> resultado = ticketRepository.findAll()
                .stream()
                .filter(t -> tecnicoId.equals(t.getAsignadoId()))
                .map(this::mapToDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(resultado);
    }

    // Tickets por email del técnico asignado
    @GetMapping("/asignado/{email}")
    public ResponseEntity<List<TicketDto>> porEmail(@PathVariable String email) {
        List<TicketDto> resultado = ticketRepository.findAll()
                .stream()
                .filter(t -> t.getAsignadoId() != null && t.getAsignadoId().equals(email))
                .map(this::mapToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(resultado);
    }

    // Cambiar solo el estado
    @PatchMapping("/{id}/estado")
    public ResponseEntity<TicketDto> cambiarEstado(
            @PathVariable String id,
            @RequestParam String nuevoEstado) {
            
        return ticketRepository.findById(id)
                .map(ticket -> {
                    ticket.setEstado(EstadoTicket.valueOf(nuevoEstado));
                    ticket.setFechaActualizacion(LocalDateTime.now());
                    return ResponseEntity.ok(mapToDto(ticketRepository.save(ticket)));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Contar tickets activos de un técnico — para el cuadro del home
    @GetMapping("/tecnico/{tecnicoId}/count")
    public ResponseEntity<Long> contarPorTecnico(@PathVariable String tecnicoId) {
        long count = ticketRepository.findAll()
                .stream()
                .filter(t -> tecnicoId.equals(t.getAsignadoId()))
                .filter(t -> !t.getEstado().name().equals("CERRADO"))
                .count();

        return ResponseEntity.ok(count);
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