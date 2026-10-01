package com.backend.repository;

import com.backend.Model.Event;
import com.backend.Model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;

import java.util.List;

public interface EventsRepo extends MongoRepository<Event, String> {
//    List<Event> findByHostUserNameUsername(String username);

    @Query(value = "{ '_id': ?0, 'hostUserName._id': ?1 }", exists = true)
    boolean existsByEventIdAndUserAndIsHostTrue(String eventId, String hostId);

    @Query("{ '_id': ?0, 'hostUserName._id': ?1 }")
    @Update("{ '$set': { 'active': ?2 } }")
    long updateEventStatus(String eventId, String hostId, Integer active);

}
