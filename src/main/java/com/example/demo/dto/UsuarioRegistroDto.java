package com.example.demo.dto;

import java.util.List;

import com.example.demo.models.Documento;
import com.example.demo.models.Rol;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioRegistroDto {
    @NotBlank(message = "El Nombre es obligatorio")
    private String nombre;
    @NotBlank(message = "El email es obligatorio")
    private String email;
    private String telefono;
    private String empresa;
    @JsonProperty("documento") 
    private Documento documento;
    @JsonProperty("usuario")
    private String usuario;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private List<Rol> roles;
}
