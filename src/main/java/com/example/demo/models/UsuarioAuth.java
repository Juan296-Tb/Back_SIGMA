package com.example.demo.models;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "UsuariosAuth")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UsuarioAuth {
    @Id
    private String id;
    private String user;
    private String pass;
    private List<Rol> roles;

}
