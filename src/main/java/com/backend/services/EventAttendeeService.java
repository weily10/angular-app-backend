package com.backend.services;

import com.backend.Model.EventAttendee;
import com.backend.Repository.EventAttendeeRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventAttendeeService {
    private final EventAttendeeRepo attendeeRepo;

    public EventAttendeeService(EventAttendeeRepo attendeeRepo) {
        this.attendeeRepo = attendeeRepo;
    }

    public EventAttendee joinEvent(String eventId, String userId) {
        // 1. Check if the user has already joined this event
        boolean alreadyJoined = attendeeRepo.findByEventIdAndUserId(eventId, userId).isPresent();

        if (alreadyJoined) {
            throw new IllegalStateException("You have already joined this event!");
        }

        // 2. Create and save the new attendee tracking record
        EventAttendee relationship = new EventAttendee(eventId, userId, "REGISTERED");
        return attendeeRepo.save(relationship);
    }

    public List<EventAttendee> getAttendeesForEvent(String eventId) {
        return attendeeRepo.findByEventId(eventId);
    }

    public long getAttendeeCount(String eventId) {
        return attendeeRepo.countByEventId(eventId);
    }
}
