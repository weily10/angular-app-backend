package com.backend.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;


@Document(collection = "events")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Event {
    @Id
    private String id; // Don't forget your ID for Mongo!
    private String title;
    private String location;
    private Instant date;
    private String summary;
    private String imgUrl; //
    @CreatedBy
    private User hostUserName;
    private Integer active;
}
