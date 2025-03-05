package com.TravelWise.TravelWise.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Record {

    // Auto generates the ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    // Ensures the correct mapping when converting from json data
    @Column(nullable = false)
    private String username; // Links the record to a specific user

    // maps the data to a column in the database
    @Column(nullable = false, length = 500)
    private String content; // Data of the record e.g. description, etc

    private LocalDateTime createdAt; // Timestamp for when the record was created

    public Record() {
        this.createdAt = LocalDateTime.now();
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}