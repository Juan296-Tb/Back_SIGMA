package com.example.demo.dto;

import java.util.List;
import com.example.demo.models.Documento;
import com.example.demo.models.Rol;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioDto {

    private String id;
    private String nombre;
    private String email;
    private String telefono;
    private String empresa;
    private Documento documento;
    private List<Rol> roles;
}