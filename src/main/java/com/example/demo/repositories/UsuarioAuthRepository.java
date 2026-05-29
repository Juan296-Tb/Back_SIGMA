package com.example.demo.repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.demo.models.UsuarioAuth;

public interface UsuarioAuthRepository extends MongoRepository<UsuarioAuth, String> {

    Optional<UsuarioAuth> findByUser(String user);

}
