package com.backend.Controller;

import com.backend.Model.Event;
import com.backend.services.EventService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/events")
@CrossOrigin(origins = "http://localhost:4200") // Allow Angular to talk to this controller
public class EventController {
    private final EventService eventService;

    public EventController(EventService eventService){
        this.eventService = eventService;
    }

    @PostMapping("/add")
    public Event addEvent(@RequestBody Event event){
        return eventService.addEvent(event);
    }

    @GetMapping("")
    public List<Event> getEvents(){
//        return eventService.getEvents();
        System.out.println(">>> THE GET REQUEST REACHED THE CONTROLLER!");
        try {
            List<Event> events = eventService.getEvents();
            System.out.println(">>> SUCCESSFULLY FETCHED: " + events.size() + " events.");
            return events;
        } catch (Exception e) {
            System.out.println(">>> ERROR IN SERVICE: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
