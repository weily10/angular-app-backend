package com.backend.Repository;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.backend.Model.Event;

import java.util.List;

public interface EventsRepo extends MongoRepository<Event, String> {
    List<Event> findByHostUserNameUsername(String username);
}
