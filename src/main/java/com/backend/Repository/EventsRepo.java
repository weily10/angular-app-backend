package com.backend.Repository;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.backend.Model.Event;

public interface EventsRepo extends MongoRepository<Event, String> {
}
