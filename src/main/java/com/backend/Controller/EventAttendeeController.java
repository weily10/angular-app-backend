package com.backend.Controller;

import com.backend.Model.EventAttendee;
import com.backend.services.EventAttendeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            @RequestParam String userId) {
        try {
            EventAttendee registration = attendeeService.joinEvent(eventId, userId);
            return ResponseEntity.ok(registration);
        } catch (IllegalStateException e) {
            // Returns a 400 Bad Request with the custom "already joined" message
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An internal error occurred.");
        }
    }
}