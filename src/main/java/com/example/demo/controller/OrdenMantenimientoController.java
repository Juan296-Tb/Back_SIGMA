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
}
