package com.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.MongoDatabaseFactory;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
public class AngularAppBackendApplication {
	@Autowired
    private MongoDatabaseFactory factory;

    public static void main(String[] args) {
        SpringApplication.run(AngularAppBackendApplication.class, args);
    }

		  @PostConstruct
    public void checkMongoDb() {
        System.out.println("Mongo DB connected to: " + factory.getMongoDatabase().getName());
    }
    
}
