package com.TravelWise.TravelWise.repository;

import com.TravelWise.TravelWise.model.HotelBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
// Based off - https://www.youtube.com/watch?v=x_nfnVU0wAI
//Adapted for use in this project with username as the search parameter
// Repositroy for the database interactions for the hotel booking

@Repository
public interface HotelBookingRepository extends JpaRepository<HotelBooking, Long> {
    List<HotelBooking> findByUsername(String username);
}