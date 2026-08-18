package com.backend.services;

import com.backend.model.Event;
import com.backend.repository.EventAttendeeRepo;
import com.backend.repository.EventsRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventService {
    private final EventsRepo eventsRepo;

    public EventService(EventsRepo eventsRepo,EventAttendeeRepo  eventAttendeeRepo) {
        this.eventsRepo = eventsRepo;
     }


    public List<Event> getEvents(){
        return eventsRepo.findAll();
    }

    public Optional<Event> getEventById(String id) {
        return eventsRepo.findById(id);
    }



}
