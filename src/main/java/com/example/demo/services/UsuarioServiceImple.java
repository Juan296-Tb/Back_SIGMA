package com.example.demo.services;
 
import java.util.List;
import java.util.UUID;
 
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
 
import com.example.demo.dto.UsuarioDto;
import com.example.demo.dto.UsuarioRegistroDto;
import com.example.demo.mapper.UsuarioMapper;
import com.example.demo.models.Rol;
import com.example.demo.models.Usuario;
import com.example.demo.models.UsuarioAuth;
import com.example.demo.repositories.UsuarioAuthRepository;
import com.example.demo.repositories.UsuarioRepository;
 
@Service
public class UsuarioServiceImple implements UsuarioService {
 
    private final UsuarioRepository userRepo;
    private final UsuarioAuthRepository authRepo;
    private final UsuarioMapper userMapper;
    private final PasswordEncoder passwordEncoder;
 
    public UsuarioServiceImple(UsuarioRepository userRepo, UsuarioAuthRepository authRepo, UsuarioMapper userMapper,
            PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.authRepo = authRepo;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }
 
    @Override
    public UsuarioDto create(UsuarioDto usuarioDto) {
        Usuario usuario = userMapper.toUsuario(usuarioDto);
        return userMapper.toDto(userRepo.save(usuario));
    }
 
    @Override
    public List<UsuarioDto> ListUsuarios() {
        return userMapper.toDtoList(userRepo.findAll());
    }
 
    @Override
    public UsuarioDto update(String id, UsuarioDto usuarioDto) {
        Usuario usuario = userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id));
        userMapper.updateUsuario(usuarioDto, usuario);
        return userMapper.toDto(userRepo.save(usuario));
    }
 
    @Override
    public void delete(String id) {
        if (!userRepo.existsById(id)) {
            throw new RuntimeException("Usuario no encontrado con id: " + id);
        }
        userRepo.deleteById(id);
    }
 
    @Override
    public UsuarioDto UsuarioByDocNum(String docnum) {
        Usuario usuario = userRepo.findByDocNum(docnum)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con número de documento: " + docnum));
        return userMapper.toDto(usuario);
    }
 
    @Override
    public UsuarioDto UsuarioByDocum(String docnum) {
        return userRepo.findByDocNum(docnum)
                .map(userMapper::toDto)
                .orElseThrow(() -> new RuntimeException(
                        "Usuario no encontrado con el documento: " + docnum));
    }
 
    @Transactional
    @Override
    public UsuarioRegistroDto registrarUsuario(UsuarioRegistroDto dto) {
 
        // 1. Asignar rol Responsable automáticamente si no viene
        if (dto.getRoles() == null || dto.getRoles().isEmpty()) {
            dto.setRoles(List.of(Rol.Responsable));
        }
 
        // 2. Guardar perfil en UsuariosPerfil
        String sharedId = UUID.randomUUID().toString();
 
        Usuario perfil = Usuario.builder()
                .id(sharedId)
                .nom(dto.getNombre())
                .email(dto.getEmail())
                .tele(dto.getTelefono())
                .empr(dto.getEmpresa())
                .doc(dto.getDocumento())
                .build();
 
        userRepo.save(perfil);
 
        // 3. Usar email como username si no viene el campo usuario
        String username = (dto.getUsuario() != null && !dto.getUsuario().isBlank())
                ? dto.getUsuario()
                : dto.getEmail();
 
        // 4. Guardar auth en UsuariosAuth
        UsuarioAuth auth = new UsuarioAuth();
        auth.setId(sharedId);
        auth.setUser(username);
        auth.setPassword(passwordEncoder.encode(dto.getPassword()));
        auth.setRoles(dto.getRoles());
 
        authRepo.save(auth);
 
        return dto;
    }
 
    @Override
    public UsuarioDto buscarPorUsername(String username) {
        // 1. Busca en UsuarioAuth por el campo "user" para obtener el sharedId
        UsuarioAuth auth = authRepo.findByUser(username)
                .orElseThrow(() -> new RuntimeException("Usuario auth no encontrado: " + username));
 
        // 2. Con ese mismo sharedId busca el perfil completo (nombre, email, etc.)
        Usuario usuario = userRepo.findById(auth.getId())
                .orElseThrow(() -> new RuntimeException("Perfil no encontrado para: " + username));
 
        return userMapper.toDto(usuario);
    }
}