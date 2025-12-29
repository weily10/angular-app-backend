package com.backend.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "Employee")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    @Id
    private String id;

    @Field("employeeId")
    private String employeeId;

    @Field("username")
    private String username;

    @Field("region")
    private String region;

    @Field("email")
    private String email;

    @Field("jobTitle")
    private String jobTitle;

    @Field("manager")
    private String manager;

    @Field("status")
    private boolean status;
}
