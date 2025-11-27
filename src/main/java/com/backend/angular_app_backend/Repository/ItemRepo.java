package com.backend.angular_app_backend.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.backend.Model.Item;

public interface ItemRepo extends MongoRepository<Item, String> {
    
}
