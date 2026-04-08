package com.example.demo.repositories;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.models.OrdenMantenimiento;

@Repository
public interface OrdenMantenimientoRepository extends MongoRepository<OrdenMantenimiento, String> {
}
