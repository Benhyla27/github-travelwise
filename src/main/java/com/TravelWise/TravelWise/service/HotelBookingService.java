package com.TravelWise.TravelWise.service;

import com.TravelWise.TravelWise.model.HotelBooking;
import com.TravelWise.TravelWise.repository.HotelBookingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

//Insperation - https://stackoverflow.com/questions/72176668/using-optionals-correctly-in-service-layer-of-spring-boot-application -
// https://www.youtube.com/watch?v=UhSEsoIUROA -
//Co-pilot created the implementation of this service class

@Service
public class HotelBookingService {

    // Interacts with repository & database for hotel booking related actions
    private final HotelBookingRepository hotelBookingRepository;

    // Constructor for the hotel booking service that injects the hotel booking repository
    public HotelBookingService(HotelBookingRepository hotelBookingRepository) {
        this.hotelBookingRepository = hotelBookingRepository;
    }

// This method saves the hotel booking to the database
    public void saveHotelBooking(HotelBooking hotelBooking) {
        hotelBookingRepository.save(hotelBooking);
    }
// This method retrieves all the bookings for a user
    public List<HotelBooking> getBookingsForUser(String username) {
        return hotelBookingRepository.findByUsername(username);
    }
}