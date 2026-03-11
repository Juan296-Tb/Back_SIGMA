package com.example.demo.repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.example.demo.models.Usuario;

public interface UsuarioRepository extends MongoRepository<Usuario, String> {

    Optional<Usuario> findByDocNumero(String docnum);

    // consulta usuario por numero documento
    @Query("{'doc.numero': ?0}")
    Optional<Usuario> findByDocNum(String docnum);
}
