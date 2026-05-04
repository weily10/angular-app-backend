package com.backend.Controller;

import com.backend.Model.Event;
import com.backend.dto.EventRequest;
import com.backend.services.EventService;
import com.backend.Repository.EventsRepo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

// IMPORTANT: Added these imports for file handling
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.List;

@RestController
@RequestMapping("/api/events")
@CrossOrigin(origins = "http://localhost:4200")
public class EventController {

    private final EventService eventService;

    @Autowired
    private EventsRepo eventRepository;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping(value = "/save", consumes = {"multipart/form-data"})
    public ResponseEntity<Event> addEvent(@ModelAttribute EventRequest request) {

        String generatedImageUrl = "";

        // 1. PHYSICAL FILE SAVING LOGIC
        if (request.getFile() != null && !request.getFile().isEmpty()) {
            try {
                // Create a unique filename so 'cat.jpg' doesn't overwrite another 'cat.jpg'
                String fileName = UUID.randomUUID().toString() + "_" + request.getFile().getOriginalFilename();

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
        event.setTitle(request.getTitle());
        event.setLocation(request.getLocation());
        event.setDate(request.getDate());
        event.setSummary(request.getSummary());
        event.setAttenders(request.getAttenders());
        event.setImgUrl(generatedImageUrl);

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
}