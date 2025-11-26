package com.backend.angular_app_backend.controllers;

import org.springframework.web.bind.annotation.RestController;

 
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;
import java.util.Arrays;

@RestController
public class Items {
  
  public class ItemController {
    @GetMapping("/items")
    public List<String> getItems() {
        return Arrays.asList("Item 1", "Item 2", "Item 3");
    }
    
    // Define item-related endpoints here
  }
}
