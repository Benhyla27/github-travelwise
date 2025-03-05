package com.TravelWise.TravelWise.controller;

import com.TravelWise.TravelWise.model.CarRental;
import com.TravelWise.TravelWise.service.CarRentalService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// For this form took inspiration from - https://www.youtube.com/watch?v=dX3ReJKG-Ek - Spring Boot Tutorial
// and https://www.youtube.com/watch?v=x_nfnVU0wAI - how to upload and display image in spring boot
// Help of co-pilot
// Stack overflow - https://stackoverflow.com/questions/67019469/how-to-upload-and-display-image-in-spring-boot - how to upload and display image in spring boot
// Tried copying as much as I could from HotelBookingController to keep consistency

@Controller
@RequestMapping("/carRentals")
public class CarRentalController {

    private static final Logger logger = LoggerFactory.getLogger(CarRentalController.class);
// Created by co-pilot for debugging

    // Injects service
    private final CarRentalService carRentalService;

    // Constructor for the controller
    public CarRentalController(CarRentalService carRentalService) {
        this.carRentalService = carRentalService;
    }

    // Utility Method to Ensure Username is Retrieved and Stored in Session
    // Checks if the username exists in the session, and logs a helpful error if it doesn't.
    private String ensureUsernameInSession(HttpSession session) {
        String username = (String) session.getAttribute("username");
        // If username is not in session, throws an exception (you can modify this to redirect if needed).
        if (username == null || username.isEmpty()) {
            throw new IllegalStateException("No username found in session. Please log in again.");
        }
        return username;
    }

    // Gets request to fetch the car rentals
    // If the username is not null it fetches the rentals for the user
    // Adds the rentals to the model for display
    @GetMapping
    public String getCarRentals(HttpSession session, Model model) {
        logger.debug("getCarRentals method called");
        // Ensures the username exists in the session before proceeding
        String username = ensureUsernameInSession(session);

        // Fetching car rentals for the user and adding them to the model
        List<CarRental> rentals = carRentalService.getRentalsForUser(username);
        model.addAttribute("carRentals", rentals);

        return "Wallet";
    }

    // Post request to create a car rental
    // If the username is not null it saves the rental for the user
    @PostMapping
    public String createCarRental(@ModelAttribute CarRental carRental, HttpSession session, Model model) {
        logger.debug("createCarRental method called");
        // Ensures the username exists in the session before proceeding
        String username = ensureUsernameInSession(session);
        // Logs the username retrieved from the session for testing
        System.out.println("DEBUG: The username retrieved from session is: " + username);

        // If username is in session, assign username to the car rental from user_accounts
        carRental.setUsername(username);
        // Saves rental using the service
        carRentalService.saveCarRental(carRental);
        logger.debug("Car rental saved for user: {}", username);

        // Retrieving the updated list of rentals for this user
        List<CarRental> rentals = carRentalService.getRentalsForUser(username);
        model.addAttribute("carRentals", rentals);

        return "Wallet";
    }
}
