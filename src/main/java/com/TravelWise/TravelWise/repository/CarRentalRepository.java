package com.TravelWise.TravelWise.repository;

import com.TravelWise.TravelWise.model.CarRental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
// Based off - https://www.youtube.com/watch?v=x_nfnVU0wAI
// Adapted for use in this project with username as the search parameter
// Repository for the database interactions for the car rental

@Repository
public interface CarRentalRepository extends JpaRepository<CarRental, Long> {
    List<CarRental> findByUsername(String username);
}

