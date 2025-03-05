package com.TravelWise.TravelWise.service;

import com.TravelWise.TravelWise.model.CarRental;
import com.TravelWise.TravelWise.repository.CarRentalRepository;
import org.springframework.stereotype.Service;

import java.util.List;

// Inspiration - https://stackoverflow.com/questions/72176668/using-optionals-correctly-in-service-layer-of-spring-boot-application -
// https://www.youtube.com/watch?v=UhSEsoIUROA -
// Co-pilot created the implementation of this service class

@Service
public class CarRentalService {

    // Interacts with repository & database for car rental related actions
    private final CarRentalRepository carRentalRepository;

    // Constructor for the car rental service that injects the car rental repository
    public CarRentalService(CarRentalRepository carRentalRepository) {
        this.carRentalRepository = carRentalRepository;
    }

    // This method saves the car rental to the database
    public void saveCarRental(CarRental carRental) {
        carRentalRepository.save(carRental);
    }

    // This method retrieves all the rentals for a user
    public List<CarRental> getRentalsForUser(String username) {
        return carRentalRepository.findByUsername(username);
    }
}
