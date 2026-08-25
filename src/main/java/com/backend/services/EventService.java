package com.backend.services;

import com.backend.Model.Event;
import com.backend.Model.EventAttendee;
import com.backend.Model.User;
import com.backend.repository.EventAttendeeRepo;
import com.backend.repository.EventsRepo;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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



    public Long cancelEvent(String eventId, String hostId){
        boolean isHost = eventsRepo.existsByEventIdAndUserAndIsHostTrue(eventId, hostId);
        System.out.println(eventId+"/"+ hostId);

        if (!isHost) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only the host can cancel this event");
        }
        return eventsRepo.updateEventStatus(eventId, hostId,0);
    }

}
