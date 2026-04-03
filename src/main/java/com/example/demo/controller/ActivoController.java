package com.example.demo.controller;

import com.example.demo.dto.ActivoDto;
import com.example.demo.models.Activo;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/activos")
@CrossOrigin(origins = "*")
public class ActivoController {

    private final MongoTemplate sigmaMongoTemplate;

    public ActivoController(@Qualifier("sigmaMongoTemplate") MongoTemplate sigmaMongoTemplate) {
        this.sigmaMongoTemplate = sigmaMongoTemplate;
    }

    @GetMapping
    public ResponseEntity<List<Activo>> getActivos() {
        List<Activo> activos = sigmaMongoTemplate.findAll(Activo.class);
        return ResponseEntity.ok(activos);
    }

    @PostMapping
    public ResponseEntity<Activo> crearActivo(@RequestBody ActivoDto dto) {
        Activo activo = new Activo(
            dto.getTitulo(),
            dto.getTipo(),
            dto.getSerie(),
            dto.getEstado(),
            dto.getResponsable(),
            dto.getDescripcion(),
            dto.getImg()
        );
        return ResponseEntity.ok(sigmaMongoTemplate.save(activo));
    }
}