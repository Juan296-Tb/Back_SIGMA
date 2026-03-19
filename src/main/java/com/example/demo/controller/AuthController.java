package com.example.demo.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.UsuarioAuthDto;
import com.example.demo.models.UsuarioAuth;
import com.example.demo.repositories.UsuarioAuthRepository;
import com.example.demo.security.JwtService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtService jwtService;
    private final AuthenticationManager authManager;
    private final UsuarioAuthRepository uar;

    public AuthController(JwtService jwtService, AuthenticationManager authManager, UsuarioAuthRepository uar) {
        this.jwtService = jwtService;
        this.authManager = authManager;
        this.uar = uar;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody UsuarioAuthDto dto) {

        // Autentificacion (Spring Security Valida usuario y contraseña)
        Authentication auth = authManager
                .authenticate(new UsernamePasswordAuthenticationToken(dto.getUsuario(), dto.getPassword()));

        // consultar usuario en Dase de datos
        UsuarioAuth usuario = uar.findByUser(dto.getUsuario())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        // 3️⃣ EXTRAER ROLES (Enum → String)
        List<String> roles = usuario.getRoles()
                .stream()
                .map(Enum::name)
                .toList();

        // 4️⃣ GENERAR TOKEN JWT
        String token = jwtService.generarToken(usuario.getUser(), roles);

        // 5️⃣ RESPUESTA (estándar tipo enterprise básica)
        Map<String, Object> respuesta = Map.of(
                "timestamp", LocalDateTime.now(),
                "status", 200,
                "mensaje", "Login exitoso",
                "usuario", usuario.getUser(),
                "roles", roles,
                "token", token);

        return ResponseEntity.ok(respuesta);
    }
}
