package com.backend.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
 

@Document(collection = "item")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Item {
    @Id
    private String uuid;
    private String title;
    private String description;
}
