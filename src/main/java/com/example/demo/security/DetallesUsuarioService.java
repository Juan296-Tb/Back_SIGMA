package com.example.demo.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.example.demo.models.UsuarioAuth;
import com.example.demo.repositories.UsuarioAuthRepository;

public class DetallesUsuarioService implements UserDetailsService {

    private final UsuarioAuthRepository uar;

    public DetallesUsuarioService(UsuarioAuthRepository uar) {
        this.uar = uar;
    }

    @Override
    public UserDetails loadUserByUsername(String username) { // Este nombre es obligatorio porque: Es parte del contrato
                                                             // de Spring Security y Spring lo llama internamente

        UsuarioAuth usuario = uar.findByUser(username).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return User.builder()
                .username(usuario.getUser()) // Establece el nombre de usuario
                .password(usuario.getPass()) // Establece la contraseña (debería estar codificada)
                .roles(usuario.getRoles().stream().map(Enum::name).toArray(String[]::new)) // Puedes asignar roles según
                                                                                           // tu lógica
                .build();
    }

}
