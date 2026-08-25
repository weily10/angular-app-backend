package com.backend.controller;

import com.backend.Model.EventAttendee;
import com.backend.services.EventAttendeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/events")
@CrossOrigin(origins = "http://localhost:4200")
public class EventAttendeeController {

    private final EventAttendeeService attendeeService;

    public EventAttendeeController(EventAttendeeService attendeeService) {
        this.attendeeService = attendeeService;
    }

    // POST /api/events/{eventId}/join?userId=xxxxxx
    @PostMapping("/{eventId}/join")
    public ResponseEntity<?> joinEvent(
            @PathVariable String eventId,
            Principal principal) {
        try {
            String usernameOrId = principal.getName();
            return ResponseEntity.ok(attendeeService.joinEvent(eventId, usernameOrId));
        } catch (IllegalStateException e) {
            // Returns a 400 Bad Request with the custom "already joined" message
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An internal error occurred.");
        }
    }

    @GetMapping("/{eventId}/attendees")
    public ResponseEntity<?> getAttendees(@PathVariable String eventId) {
        try {
            List<EventAttendee> attendees = attendeeService.getAttendeesForEvent(eventId);
            return ResponseEntity.ok(attendees);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }





}