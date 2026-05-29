package com.example.demo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.UsuarioDto;
import com.example.demo.dto.UsuarioRegistroDto;
import com.example.demo.services.UsuarioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
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
}