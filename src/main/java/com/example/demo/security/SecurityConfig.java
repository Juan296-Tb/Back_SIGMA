package com.example.demo.security;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class SecurityConfig {

    private final DetallesUsuarioService detallesUsuarioService;
    private final PasswordEncoder passwordEncoder;

    public SecurityConfig(DetallesUsuarioService detallesUsuarioService, PasswordEncoder passwordEncoder) {
        this.detallesUsuarioService = detallesUsuarioService;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Usamos configuración CORS definida abajo
            .csrf(csrf -> csrf.disable()) // Desactivar CSRF para APIs REST
            .authorizeHttpRequests(auth -> auth
                // Permitir todas las peticiones OPTIONS para evitar problemas CORS preflight
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                // Permitir acceso sin autenticación a endpoints de login y registro
                .requestMatchers("/auth/**", "/api/usuarios/registrar").permitAll()
                // Cualquier otra petición requiere autenticación
                .anyRequest().authenticated()
            );
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        // Cambia o agrega orígenes si cambias puerto o deployas en otro lado
        config.setAllowedOrigins(List.of("http://localhost:5174"));
        // Métodos permitidos para el frontend
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        // Headers permitidos, puedes ser más específico si quieres
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true); // Permite envío de cookies y headers de auth

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config); // Aplica a todas las rutas
        return source;
    }

    // Configura AuthenticationManager con tu UserDetailsService y PasswordEncoder
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
            .userDetailsService(detallesUsuarioService)
            .passwordEncoder(passwordEncoder)
            .and()
            .build();
    }
}