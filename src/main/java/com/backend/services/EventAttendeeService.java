package com.backend.services;

import com.backend.Model.Event;
import com.backend.Model.EventAttendee;
import com.backend.Model.User;
import com.backend.Repository.EventAttendeeRepo;
import com.backend.Repository.EventsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventAttendeeService {
    private final EventAttendeeRepo attendeeRepo;
    @Autowired
    private  EventsRepo eventsRepo;

    public EventAttendeeService(EventAttendeeRepo attendeeRepo) {
        this.attendeeRepo = attendeeRepo;
    }

    public EventAttendee joinEvent(String eventId, String user) {
        // 1. Check if the user has already joined this event
        boolean alreadyJoined = attendeeRepo.findByEventIdAndUserId(eventId, user).isPresent();

        Event event = eventsRepo.findById(eventId).orElseThrow(() -> new IllegalArgumentException("Event not found"));

        if (alreadyJoined) {
            throw new IllegalStateException("You have already joined this event!");
        }

        User host = event.getHostUserName();
                if(host != null){
                    String hostIdentifier = host.getEmail();
                    System.out.println("user:"+ user +"hostIdentifier"+hostIdentifier);
                    if (user.equals(hostIdentifier)) {
                        throw new IllegalStateException("The host cannot join their own event.");
                    }
                }


        // 2. Create and save the new attendee tracking record
        EventAttendee relationship = new EventAttendee(eventId, user, "REGISTERED");
        return attendeeRepo.save(relationship);
    }

    public List<EventAttendee> getAttendeesForEvent(String eventId) {
        return attendeeRepo.findByEventId(eventId);
    }

    public long getAttendeeCount(String eventId) {
        return attendeeRepo.countByEventId(eventId);
    }
}
