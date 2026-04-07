package com.example.demo.controller;

import com.example.demo.dto.ActivoDto;
import com.example.demo.models.Activo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    @PutMapping("/{id}/responsable")
    public ResponseEntity<Activo> actualizarResponsable(
            @PathVariable String id,
            @RequestBody Map<String, String> body) {

        Query query = new Query(Criteria.where("_id").is(new ObjectId(id)));
        Update update = new Update().set("responsable", body.get("responsable"));
        sigmaMongoTemplate.updateFirst(query, update, Activo.class);

        Activo activo = sigmaMongoTemplate.findOne(query, Activo.class);
        if (activo == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(activo);
    }
}