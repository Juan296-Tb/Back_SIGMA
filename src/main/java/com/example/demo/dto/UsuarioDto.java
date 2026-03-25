package com.example.demo.dto;

import com.example.demo.models.Documento;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioDto {

    private String id; // ID único para cada usuario el mismo del autentificación
    private String nombre; // nombre del usuario
    private String email;
    private String telefono;
    private String empresa;
    private Documento documento; // correo electrónico del usuario

}
