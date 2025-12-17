package com.backend.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "Employee")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Employee {
    @Id
    private String _id;
    private String employeeId;
    private String username;
    private String region;
    private String email;
    private String jobTitle;
    private String manager;
    private boolean status;

}
