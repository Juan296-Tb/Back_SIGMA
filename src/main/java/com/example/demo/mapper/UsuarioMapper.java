package com.example.demo.mapper;

import java.util.List;

import com.example.demo.dto.UsuarioDto;
import com.example.demo.models.Usuario;

public interface UsuarioMapper {
    Usuario toUsuario(UsuarioDto usuarioDto); // Método para convertir de UsuarioDto a Usuario

    UsuarioDto toDto(Usuario usuario); // Método para convertir de Usuario a UsuarioDto

    List<UsuarioDto> toDtoList(List<Usuario> usuarios); // Método para convertir una lista de Usuario a una lista de
                                                        // UsuarioDto

    void updateUsuario(UsuarioDto usuarioDto, Usuario usuario); // Método para actualizar un Usuario existente con los
                                                                // datos de un UsuarioDto

}
