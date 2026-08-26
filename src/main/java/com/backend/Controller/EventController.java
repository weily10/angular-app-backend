package com.backend.controller;

import com.backend.Model.User;
import com.backend.dto.EventRequest;
import com.backend.Model.Event;
import com.backend.Model.EventAttendee;
import com.backend.repository.EventAttendeeRepo;
import com.backend.repository.EventsRepo;
import com.backend.repository.UserRepository;
import com.backend.services.EventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/events")
@CrossOrigin(origins = "http://localhost:4200")
public class EventController {

    private final EventService eventService;
    private final UserRepository userRepository;
    private final EventsRepo eventRepository;
    private final EventAttendeeRepo attendeeRepository;

    public EventController(EventService eventService,
                           UserRepository userRepository,
                           EventsRepo eventRepository,
                           EventAttendeeRepo attendeeRepository) {
        this.eventService = eventService;
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
        this.attendeeRepository = attendeeRepository;
    }

    @PostMapping(value = "/save", consumes = {"multipart/form-data"})
    public ResponseEntity<Event> addEvent(@ModelAttribute EventRequest request, Authentication authentication) {

        String generatedImageUrl = "";

        // 1. PHYSICAL FILE SAVING LOGIC
        if (request.getFile() != null && !request.getFile().isEmpty()) {
            try {
                // Create a unique filename so 'cat.jpg' doesn't overwrite another 'cat.jpg'
                String fileName = UUID.randomUUID() + "_" + request.getFile().getOriginalFilename();

                // Point to the 'uploads' folder in your project root
                String uploadDir = "uploads/";
                Path path = Paths.get(uploadDir + fileName);

                // Create the folder if it doesn't exist
                Files.createDirectories(path.getParent());

                // WRITE the file bytes to your hard drive
                Files.write(path, request.getFile().getBytes());

                // This is the URL Angular will use to reach the file
                generatedImageUrl = "http://localhost:8080/uploads/" + fileName;

            } catch (IOException e) {
                System.out.println(">>> FILE ERROR: " + e.getMessage());
                return ResponseEntity.internalServerError().build();
            }
        }

        // 2. Map DTO to Entity
        Event event = new Event();

        String email = authentication.getName();
        System.out.print("assas"+email);
        User currentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        event.setTitle(request.getTitle());
        event.setLocation(request.getLocation());
        event.setDate(request.getDate());
        event.setSummary(request.getSummary());
         event.setImgUrl(generatedImageUrl);
         event.setActive(1);
        event.setHostUserName(currentUser);

        Event savedEvent = eventRepository.save(event);
        if (savedEvent.getHostUserName() != null) {
            EventAttendee hostAttendee = new EventAttendee();
            hostAttendee.setEventId(savedEvent.getId());

            // Use .getId() or .getUsername() depending on your User entity structure
            hostAttendee.setUserId(savedEvent.getHostUserName().getId());

            attendeeRepository.save(hostAttendee);
        }

        // 3. Save to MongoDB
        return ResponseEntity.ok(eventRepository.save(event));
    }

    @GetMapping("")
    public List<Event> getEvents() {
        try {
            return eventService.getEvents();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable String id){
        return eventService.getEventById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{eventId}/{hostId}/cancelEvent")
    public ResponseEntity<Long> cancelEvent(
            @PathVariable("eventId") String eventId,
            @PathVariable("hostId") String hostId) {
        try {
            System.out.println("hostId: " + hostId);
            return ResponseEntity.ok(eventService.cancelEvent(eventId, hostId));
        } catch (RuntimeException e) {
            log.error("Failed to cancel event {} for host {}: {}", eventId, hostId, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}