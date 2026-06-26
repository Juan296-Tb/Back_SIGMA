package com.example.demo.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.UsuarioDto;
import com.example.demo.dto.UsuarioRegistroDto;
import com.example.demo.services.UsuarioService;
import com.example.demo.models.Ticket;
import com.example.demo.models.Activo;
import com.example.demo.repositories.TicketRepository;
import com.example.demo.repositories.ActivoRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final TicketRepository ticketRepository;
    private final ActivoRepository activoRepository;

    public UsuarioController(
            UsuarioService usuarioService,
            TicketRepository ticketRepository,
            ActivoRepository activoRepository) {

        this.usuarioService = usuarioService;
        this.ticketRepository = ticketRepository;
        this.activoRepository = activoRepository;
    }


    @PostMapping()
    public ResponseEntity<UsuarioDto> crear(@RequestBody UsuarioDto usuarioDto) {
        UsuarioDto creado = usuarioService.create(usuarioDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @GetMapping
    public ResponseEntity<List<UsuarioDto>> todos() {
        return ResponseEntity.ok(usuarioService.ListUsuarios());
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDto> actualizar(@PathVariable String id, @RequestBody UsuarioDto usuarioDto) {
        return ResponseEntity.ok(usuarioService.update(id, usuarioDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable String id) {
        usuarioService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/docnum/{docnum}")
    public ResponseEntity<UsuarioDto> userDocNum(@PathVariable String docnum) {
        return ResponseEntity.ok(usuarioService.UsuarioByDocNum(docnum));
    }

    @GetMapping("/docnum2/{docnum}")
    public ResponseEntity<?> userDocNum2(@PathVariable String docnum) {
        try {
            UsuarioDto usuario = usuarioService.UsuarioByDocNum(docnum);
            return ResponseEntity.ok(usuario);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @GetMapping("/docnum3/{docnum}")
    public ResponseEntity<UsuarioDto> userDocNum3(@PathVariable String docnum) {
        UsuarioDto usuario = usuarioService.UsuarioByDocum(docnum);
        return ResponseEntity.ok(usuario);
    }

    @PostMapping("/registrar")
    public ResponseEntity<?> registrar(@Valid @RequestBody UsuarioRegistroDto dto) {
        UsuarioRegistroDto creado = usuarioService.registrarUsuario(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @GetMapping("/ActRes")
    public ResponseEntity<UsuarioDto> usuarioActual() {
        String username = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();
        return ResponseEntity.ok(usuarioService.buscarPorUsername(username));
    }

    @PutMapping("/{id}/rol")
    public ResponseEntity<?> cambiarRol(
            @PathVariable String id,
            @RequestBody Map<String, String> body) {

        usuarioService.cambiarRol(id, body.get("rol"));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/por-rol")
    public ResponseEntity<List<UsuarioDto>> porRol(@RequestParam String rol) {
        return ResponseEntity.ok(usuarioService.listarPorRol(rol));
    }


    @GetMapping("/{id}/dashboard")
    public ResponseEntity<Map<String, Object>> dashboard(@PathVariable String id) {

        // TICKETS asignados al usuario
        List<Ticket> tickets = ticketRepository.findAll()
                .stream()
                .filter(t -> id.equals(t.getAsignadoId()))
                .collect(Collectors.toList());

        // ACTIVOS asignados al usuario
        List<Activo> activos = activoRepository.findAll()
                .stream()
                .filter(a -> id.equals(a.getResponsable()))
                .collect(Collectors.toList());

        // respuesta para Flutter
        Map<String, Object> response = Map.of(
                "nombre", id, // luego puedes cambiarlo por nombre real si quieres
                "tickets", tickets,
                "activos", activos
        );

        return ResponseEntity.ok(response);
    }
}