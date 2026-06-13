package com.example.demo.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class MongoConfigSigma {

    @Value("${spring.data.mongodb.uri}")
    private String registroUri;

    @Value("${sigma.mongodb.uri}")
    private String sigmaUri;

    @Bean(name = "mongoClient")
    @Primary
    public MongoClient mongoClient() {
        return MongoClients.create(registroUri);
    }

    @Bean(name = "mongoTemplate")
    @Primary
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoClient(), "RegistroUser");
    }

    @Bean(name = "sigmaMongoClient")
    public MongoClient sigmaMongoClient() {
        return MongoClients.create(sigmaUri);
    }

    @Bean(name = "sigmaMongoTemplate")
    public MongoTemplate sigmaMongoTemplate() {
        return new MongoTemplate(sigmaMongoClient(), "sigma_activos");
    }
}