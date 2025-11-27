package com.backend.angular_app_backend.controllers;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;
import java.util.Arrays;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class ItemsController {
    @GetMapping("/items")
    public List<Map<String, String>> getItems() {
        return Arrays.asList(
            Map.of("title", "Item 1", "description", "Description for Item 1"),
            Map.of("title", "Item 2", "description", "Description for Item 2"),
            Map.of("title", "Item 3", "description", "Description for Item 3")
        );
    }

}
