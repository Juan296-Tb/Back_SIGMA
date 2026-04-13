package com.example.demo.mapper;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.example.demo.dto.UsuarioDto;
import com.example.demo.models.Usuario;
import com.example.demo.models.UsuarioAuth;
import com.example.demo.repositories.UsuarioAuthRepository;

@Component
public class UsuarioMapperImpl implements UsuarioMapper {

    private final UsuarioAuthRepository authRepo;

    public UsuarioMapperImpl(UsuarioAuthRepository authRepo) {
        this.authRepo = authRepo;
    }

    @Override
    public Usuario toUsuario(UsuarioDto usuarioDto) {
        if (usuarioDto == null)
            return null;
        return Usuario.builder()
                .id(usuarioDto.getId())
                .nom(usuarioDto.getNombre())
                .email(usuarioDto.getEmail())
                .tele(usuarioDto.getTelefono())
                .empr(usuarioDto.getEmpresa())
                .doc(usuarioDto.getDocumento())
                .build();
    }

    @Override
    public UsuarioDto toDto(Usuario usuario) {
        if (usuario == null)
            return null;

        List<com.example.demo.models.Rol> roles = null;
        Optional<UsuarioAuth> auth = authRepo.findById(usuario.getId());
        if (auth.isPresent()) {
            roles = auth.get().getRoles();
        }

        return UsuarioDto.builder()
                .id(usuario.getId())
                .nombre(usuario.getNom())
                .email(usuario.getEmail())
                .telefono(usuario.getTele())
                .empresa(usuario.getEmpr())
                .documento(usuario.getDoc())
                .roles(roles)
                .build();
    }

    @Override
    public List<UsuarioDto> toDtoList(List<Usuario> usuarios) {
        if (usuarios == null)
            return null;
        return usuarios.stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public void updateUsuario(UsuarioDto usuarioDto, Usuario usuario) {
        if (usuario == null) {
            throw new IllegalArgumentException("La entidad Usuario no puede ser nula");
        }
        if (usuarioDto == null) {
            throw new IllegalArgumentException("El DTO UsuarioPerfilDTO no puede ser nulo");
        }
        usuario.setNom(usuarioDto.getNombre());
        usuario.setEmail(usuarioDto.getEmail());
        usuario.setTele(usuarioDto.getTelefono());
        usuario.setEmpr(usuarioDto.getEmpresa());
        usuario.setDoc(usuarioDto.getDocumento());
    }
}