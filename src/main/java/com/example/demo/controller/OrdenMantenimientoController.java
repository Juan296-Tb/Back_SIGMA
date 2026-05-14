package com.example.demo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.OrdenMantenimientoDto;
import com.example.demo.services.OrdenMantenimientoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/ordenes")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class OrdenMantenimientoController {

    private final OrdenMantenimientoService service;

    @PostMapping
    public ResponseEntity<OrdenMantenimientoDto> crear(@Validated @RequestBody OrdenMantenimientoDto dto) {
        return ResponseEntity.ok(service.crear(dto));
    }

    @GetMapping
    public ResponseEntity<List<OrdenMantenimientoDto>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrdenMantenimientoDto> obtener(@PathVariable String id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrdenMantenimientoDto> actualizar(
            @PathVariable String id,
            @Validated @RequestBody OrdenMantenimientoDto dto) {
        return ResponseEntity.ok(service.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable String id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    // Filtrar por estado y/o prioridad — para los botones de filtro del front
    @GetMapping("/filtrar")
    public ResponseEntity<List<OrdenMantenimientoDto>> filtrar(
            @RequestParam(required = false) String estado,
            @RequestParam(required = false) String prioridad) {
        return ResponseEntity.ok(service.filtrar(estado, prioridad));
    }

    // Órdenes de un técnico específico — para el home del técnico
    @GetMapping("/tecnico/{tecnicoId}")
    public ResponseEntity<List<OrdenMantenimientoDto>> porTecnico(@PathVariable String tecnicoId) {
        return ResponseEntity.ok(service.listarPorTecnico(tecnicoId));
    }

    // Cambiar solo el estado — para el PATCH rápido desde el front
    @PatchMapping("/{id}/estado")
    public ResponseEntity<OrdenMantenimientoDto> cambiarEstado(
            @PathVariable String id,
            @RequestParam String nuevoEstado) {
        return ResponseEntity.ok(service.cambiarEstado(id, nuevoEstado));
    }
}
