package com.example.demo.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.models.Ticket;

@Repository
public interface TicketRepository extends MongoRepository<Ticket, String> {
    
}