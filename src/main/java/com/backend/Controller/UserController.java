package com.backend.Controller;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public class UserController {

    @Id
    private String id;

    private String email;
    private String password;
    private String username;

    // getters / setters
}
