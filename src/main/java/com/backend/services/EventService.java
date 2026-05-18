package com.backend.services;

import com.backend.Model.Event;
import com.backend.Repository.EventsRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventService {
    private final EventsRepo eventsRepo;

    public EventService(EventsRepo eventsRepo) {
        this.eventsRepo = eventsRepo;
    }

    public Event addEvent(Event event) {
        return eventsRepo.save(event);
    }

    public List<Event> getEvents(){
        return eventsRepo.findAll();
    }

    public Optional<Event> getEventById(String id) {
        return eventsRepo.findById(id);
    }

}
