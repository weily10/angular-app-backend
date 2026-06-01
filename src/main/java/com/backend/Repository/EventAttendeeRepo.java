package com.backend.Repository;

import com.backend.Model.EventAttendee;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface EventAttendeeRepo extends MongoRepository<EventAttendee, String> {
    Optional<EventAttendee> findByEventIdAndUserId(String eventId, String userId);
    List<EventAttendee> findByEventId(String eventId);
    long countByEventId(String eventId);

}
