package com.TravelWise.TravelWise.repository;

import com.TravelWise.TravelWise.model.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

// Based off - https://www.youtube.com/watch?v=x_nfnVU0wAI
// Adapted for use in this project with username as the search parameter
// Repository for the database interactions for the car rental

public interface RecordRepository extends JpaRepository<Record, Long> {
    List<Record> findByUsername(String username); // Fetch records for a specific user
}
