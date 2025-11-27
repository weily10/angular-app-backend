package com.backend.angular_app_backend.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
 

@RestController
public class HelloController {
  @GetMapping("/")
   
  public String hello() {
    return "Hello, World!";
  }
}
