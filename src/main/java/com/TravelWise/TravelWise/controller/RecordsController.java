package com.TravelWise.TravelWise.controller;

import com.TravelWise.TravelWise.dto.RecordDTO;
import com.TravelWise.TravelWise.model.*;
import com.TravelWise.TravelWise.service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

// https://www.youtube.com/watch?v=8SGI_XS5OPw - Used for the creation of the controller class
// https://docs.spring.io/spring-boot/reference/data/sql.html
// https://stackoverflow.com/questions/63712187/java-records-jep359-as-spring-controller-request-and-response-dtos
// Controller for handling the records page
// This controller is responsible for fetching all records for a given user e.g expenses, boarding passes, car rentals, hotel bookings
// It fetches the data from respective services and combines them into a single list for rendering
@Controller
public class RecordsController {

    // Injects the following services for use in the controller
    private final ExpenseService expenseService;
    private final CarRentalService carRentalService;
    private final BoardingPassService boardingPassService;
    private final HotelBookingService hotelBookingService;

    // Constructor for dependency injection
    // Spring automatically injects the services into the controller
    public RecordsController(ExpenseService expenseService,
                             CarRentalService carRentalService,
                             BoardingPassService boardingPassService,
                             HotelBookingService hotelBookingService) {
        this.expenseService = expenseService;
        this.carRentalService = carRentalService;
        this.boardingPassService = boardingPassService;
        this.hotelBookingService = hotelBookingService;
    }

    // Handles the get request for the records page
    // Fetches all records for a given user and combines them into a single list
    // Completed this section with the aid of ChatGPT 43-60
    @GetMapping("/records")
    public String showRecordsPage(HttpSession session, Model model) {
        // Retrieve the username from the session
        String username = (String) session.getAttribute("username");
        if (username == null || username.isEmpty()) {
            return "redirect:/login";
        }

        // Gather all data from services for the given username
        List<RecordDTO> boardingPasses = getBoardingPassRecords(username);
        List<RecordDTO> expenses = getExpenseRecords(username);
        List<RecordDTO> carRentals = getCarRentalRecords(username);
        List<RecordDTO> hotelBookings = getHotelBookingRecords(username);


        // Add all data to model and serve the view
        model.addAttribute("boardingPasses", boardingPasses);
        model.addAttribute("expenses", expenses);
        model.addAttribute("carRentals", carRentals);
        model.addAttribute("hotelBookings", hotelBookings);

        return "records";
    }

    // Retrieves expense-based records
    // Uses the DTO to store the record type and details
    // The aid of sources and chatGPT
    private List<RecordDTO> getExpenseRecords(String username) {
        List<ExpenseModel> expenses = expenseService.getUserExpenses(username);
        List<RecordDTO> expenseRecords = new ArrayList<>();
        for (ExpenseModel expense : expenses) {
            expenseRecords.add(new RecordDTO(
                    "Expense",
                    //Chatgpt
                    String.format("Name: %s | Amount: %s %s | Notes: %s",
                            expense.getExpenseName(),
                            expense.getAmount(),
                            expense.getCurrency() != null ? expense.getCurrency() : "N/A",
                            expense.getNotes() != null ? expense.getNotes() : "None"
                    )
            ));
        }
        return expenseRecords;
    }

    // Retrieves the Car Rental-based records
    // Uses the DTO to store the record type and details
    private List<RecordDTO> getCarRentalRecords(String username) {
        List<CarRental> rentals = carRentalService.getRentalsForUser(username);
        List<RecordDTO> rentalRecords = new ArrayList<>();
        for (CarRental rental : rentals) {
            rentalRecords.add(new RecordDTO(
                    "Car Rental",
                    String.format("Car Name: %s | Start Date: %s | End Date: %s | Covered by Insurance: %s",
                            rental.getCarName(),
                            rental.getPickUpDate(),
                            rental.getDropOffDate(),
                            rental.isInsuranceCover() ? "Yes" : "No"
                    )
            ));
        }
        return rentalRecords;
    }

    // Retrieves the Boarding Pass-based records
    // Uses the DTO to store the record type and details
    private List<RecordDTO> getBoardingPassRecords(String username) {
        List<BoardingPass> boardingPasses = boardingPassService.getBoardingPassesForUser(username);
        List<RecordDTO> boardingPassRecords = new ArrayList<>();
        for (BoardingPass boardingPass : boardingPasses) {
            boardingPassRecords.add(new RecordDTO(
                    "Boarding Pass",
                    "File Name: " + boardingPass.getFileName()
            ));
        }
        return boardingPassRecords;
    }

    // Retrieves the Hotel Booking-based records
    // Uses the DTO to store the record type and details
    private List<RecordDTO> getHotelBookingRecords(String username) {
        List<HotelBooking> bookings = hotelBookingService.getBookingsForUser(username);
        List<RecordDTO> bookingRecords = new ArrayList<>();
        for (HotelBooking booking : bookings) {
            bookingRecords.add(new RecordDTO(
                    "Hotel Booking",
                    String.format("Hotel: %s | Address: %s | Price Paid: %s | Check-in: %s | Check-out: %s",
                            booking.getHotelName(),
                            booking.getAddress() != null ? booking.getAddress() : "N/A",
                            booking.getPricePaid() != null ? "$" + booking.getPricePaid() : "N/A",
                            booking.getCheckInDate(),
                            booking.getCheckOutDate()
                    )
            ));
        }
        return bookingRecords;
    }
    @GetMapping("/records-diagnostic")
    @ResponseBody  // This annotation makes the method return plain text instead of looking for a view
    public String recordsDiagnostic() {
        return "Records diagnostic page is working correctly";
    }
}