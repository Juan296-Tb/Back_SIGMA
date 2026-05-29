package com.example.demo.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.repositories.UsuarioAuthRepository;

@Service
public class DetallesUsuarioService implements UserDetailsService {

    private final UsuarioAuthRepository uar;

    public DetallesUsuarioService(UsuarioAuthRepository uar) {
        this.uar = uar;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return uar.findByUser(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "Usuario no encontrado: " + username));
    }
}