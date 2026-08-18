package com.backend.repository;

import com.backend.model.Event;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface EventsRepo extends MongoRepository<Event, String> {
    List<Event> findByHostUserNameUsername(String username);
}
