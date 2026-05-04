package com.backend.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.Date;

@Data
public class EventRequest {
    private String title;
    private String location;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Instant date;
    private String summary;
    private Integer attenders;
    private MultipartFile file;
}
