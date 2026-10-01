package com.backend.repository;

import com.backend.Model.Item;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ItemRepo extends MongoRepository<Item, String> {
    
}
