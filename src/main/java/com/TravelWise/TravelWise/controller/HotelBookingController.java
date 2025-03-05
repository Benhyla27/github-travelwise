package com.TravelWise.TravelWise.controller;

import com.TravelWise.TravelWise.model.HotelBooking;
import com.TravelWise.TravelWise.service.HotelBookingService;
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
// Tried copying as much as I could from BoardingPassController to keep consistancy


@Controller
@RequestMapping("/hotelBookings")
public class HotelBookingController {

    private static final Logger logger = LoggerFactory.getLogger(HotelBookingController.class);
//Created by co-pilot for debugging

    //Injects service
    private final HotelBookingService hotelBookingService;

    //Constructor for the controller
    public HotelBookingController(HotelBookingService hotelBookingService) {
        this.hotelBookingService = hotelBookingService;
    }

//Gets request to fetch the hotel bookings
//If the username is not null it fetches the bookings for the user
//Adds the bookings to the model for display

    @GetMapping
    public String getHotelBookings(HttpSession session, Model model) {
        String username = (String) session.getAttribute("username");
        if (username != null) {
            List<HotelBooking> bookings = hotelBookingService.getBookingsForUser(username);
            model.addAttribute("bookings", bookings);
        }
        return "Wallet";
    }

    //Post request to create a hotel booking
    //If the username is not null it saves the booking for the user
    @PostMapping
    public String createHotelBooking(@ModelAttribute HotelBooking hotelBooking, HttpSession session, Model model) {
        logger.debug("createHotelBooking method called");
        String username = (String) session.getAttribute("username");
        //If username is in session assign username to that hotlbooking from user_accounts
        //Saves booking using the service
        if (username != null) {
            hotelBooking.setUsername(username);
            hotelBookingService.saveHotelBooking(hotelBooking);
            logger.debug("Hotel booking saved for user: {}", username);

            // Retrieve updated list of bookings and add to model
            List<HotelBooking> bookings = hotelBookingService.getBookingsForUser(username);
            model.addAttribute("bookings", bookings);
        } else {
            //Logs message if no user was found for debugging and testing by myself
            logger.debug("No username found in session");
        }
        return "Wallet";
    }
}