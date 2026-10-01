package com.backend.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document(collection = "event_attendees")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventAttendee {
    @Id
    private String id;
    private String eventId;
    private String userId;
    private Instant joinedAt;
    private String status;


    public EventAttendee(String eventId, String userId, String status) {
        this.eventId = eventId;
        this.userId = userId;
        this.joinedAt = Instant.now();
        this.status = status;
    }

}
