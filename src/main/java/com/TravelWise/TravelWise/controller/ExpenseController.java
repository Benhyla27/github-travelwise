package com.TravelWise.TravelWise.controller;

import com.TravelWise.TravelWise.model.ExpenseModel;
import com.TravelWise.TravelWise.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

// For this form took inspiration from - https://www.youtube.com/watch?v=dX3ReJKG-Ek - Spring Boot Tutorial
// and https://www.youtube.com/watch?v=x_nfnVU0wAI - how to upload and display image in spring boot
// Help of co-pilot
// Stack overflow - https://stackoverflow.com/questions/67019469/how-to-upload-and-display-image-in-spring-boot - how to upload and display image in spring boot
// Tried copying as much as I could from HotelBookingController to keep consistency

// Controller class for handling expense requests
@Controller
@RequestMapping("/expenses")
public class ExpenseController {

    //Automatically injects the ExpenseService class to be used
    @Autowired
    private ExpenseService expenseService;

    //Handles the get request for the expenses page
    //If the username is present it fetches and displays the expenses for the user
    @GetMapping
    public String showExpensesPage(HttpSession session, Model model) {
        //Retrieves username from the session
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return "redirect:/login"; // Redirect user to login if session does not exist
        }
        model.addAttribute("username", username);
        // Gets the list of expenses specific to the user from the database
        List<ExpenseModel> expenses = expenseService.getUserExpenses(username);
        model.addAttribute("expenses", expenses);
        return "Expense";
    }

    // Handles the post request for adding data to the database
    //Only if the username is present
    // Certain pieces of data are required, others are optional
    @PostMapping("/add")
    public String addExpense(
            @RequestParam String expenseName,
            @RequestParam Double amount,
            @RequestParam(required = false) Double vat,
            @RequestParam(required = false) String currency,
            @RequestParam(required = false) String paymentMethod,
            @RequestParam(required = false) String notes,
            @RequestParam(required = false) String expenseDate,
            HttpSession session
    ) throws IOException {
        //Retrieves the username from the session
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return "redirect:/login";
        }

        //Creates a new ExpenseModel object and sets the values
        //Saves the expense to the database
        //Sets the data with the associated username
        //Chatgpt
        ExpenseModel expense = new ExpenseModel();
        expense.setUsername(username);
        expense.setExpenseName(expenseName);
        expense.setAmount(amount != null ? BigDecimal.valueOf(amount) : BigDecimal.ZERO);
        expense.setVat(vat != null ? BigDecimal.valueOf(vat) : null);
        expense.setCurrency(currency);
        expense.setPaymentMethod(paymentMethod);
        expense.setNotes(notes);
        expense.setExpenseDate(expenseDate != null ? java.time.LocalDate.parse(expenseDate) : null);

        //Redirects the user back to the expense form once the data is saved
        expenseService.saveExpense(expense);
        return "redirect:/expenses";
    }



}