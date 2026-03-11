package com.example.demo.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "UsuariosPerfil")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Usuario {
    @Id
    private String id; // ID único para cada usuario el mismo del autentificación
    private String nom; // nombre del usuario
    private String ape; // apellido del usuario
    private Documento doc; // documento de identidad del usuario objeto con tipo y número
    private Direccion dir; // dirección del usuario objeto con calle, numero, ciudad, y código postal
    // private List<Direccion> dire; // lista de direcciones
    private String email; // correo electrónico del usuario

}
