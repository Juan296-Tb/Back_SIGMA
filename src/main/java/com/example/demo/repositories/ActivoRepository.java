package com.example.demo.repositories;

import com.example.demo.models.Activo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivoRepository extends MongoRepository <Activo, String> {

}
