package com.TravelWise.TravelWise.controller;

//https://www.youtube.com/watch?v=x_nfnVU0wAI - Used for the creation of the controller class

import com.TravelWise.TravelWise.model.BoardingPass;
import com.TravelWise.TravelWise.model.CarRental;
import com.TravelWise.TravelWise.model.HotelBooking;
import com.TravelWise.TravelWise.model.UserModel;
import com.TravelWise.TravelWise.service.CarRentalService;
import com.TravelWise.TravelWise.service.HotelBookingService;
import com.TravelWise.TravelWise.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.TravelWise.TravelWise.repository.BoardingPassRepository;
import java.util.List;

// Controller class for handling user requests
@Controller
public class UserController {

    //Autowired annotation is used for automatic dependency injection
    //Changed to private final by recomendation of co-pilot
    private final UserService userService;
    private final BoardingPassRepository boardingPassRepository;
    private final CarRentalService carRentalService;

    @Autowired
    private HotelBookingService hotelBookingService;


    //Constructor for UserController to initialize userService and boardingPassRepository
    public UserController(UserService userService, BoardingPassRepository boardingPassRepository, CarRentalService carRentalService) {
        this.userService = userService;
        this.boardingPassRepository = boardingPassRepository;
        this.carRentalService = carRentalService;
    }

    //Shows the sign up page and passes a new UserModel object to the page
    @GetMapping("/signup")
    public String showSignUpPage(Model model) {
        model.addAttribute("signupRequest", new UserModel());
        return "SignUp_page";
    }

    //Shows the login page and passes a new UserModel object to the page
    @GetMapping("/login")
    public String showLoginPage(Model model) {
        model.addAttribute("loginRequest", new UserModel());
        return "Login_page";
    }

    //Handles the login request and authenticates the user
    @PostMapping("/login")
    public String login(@ModelAttribute UserModel userModel, Model model, HttpSession session) {
        // Print out the login request for debugging and test
        System.out.println("login request: " + userModel);
        // Get the user id from the username
        Integer userid = userService.getUserIdByUsername(userModel.getUsername());
        // Authenticate the user by verifying username and password from the database
        UserModel authenticated = userService.authenticate(userid, userModel.getPassword());
        if (authenticated != null) {
            //Stores the username in the session and passes the username and userid to the dashboard
            // This was implemented due to my problems with principal object and security config
            session.setAttribute("username", authenticated.getUsername());
            model.addAttribute("userLogin", authenticated.getUsername());
            model.addAttribute("userid", authenticated.getUserid());
            return "redirect:/dashboard";
        }
        model.addAttribute("loginError", true);
        return "Login_page";
    }

    //Handles the signup request and registers the user
    @PostMapping("/signup")
    public String signup(@ModelAttribute UserModel userModel, Model model) {
        // print out the signup request for debugging and testing
        System.out.println("signup request: " + userModel);
        // Register the user and return the registered user pushes to database
        UserModel registeredUser = userService.registerUser(userModel.getUsername(), userModel.getPassword(), userModel.getEmail());
        if (registeredUser != null) {
            return "Login_page";
        } else {
            model.addAttribute("signupError", true);
            return "error_page";
        }
    }

    //Displays the wallet page
    @GetMapping("/Wallet")
    public String showWalletPage(HttpSession session, Model model) {
        // Retrieve username from session
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return "redirect:/login";
        }

        // Fetch Boarding Passes
        List<BoardingPass> boardingPasses = boardingPassRepository.findByUser_Username(username);
        System.out.println("DEBUG - BoardingPasses: " + boardingPasses);
        model.addAttribute("boardingPasses", boardingPasses);

        // Check if boarding pass list is not empty and set imageId for the first boarding pass
        if (!boardingPasses.isEmpty()) {
            // Add the ID of the first boarding pass to the model
            model.addAttribute("imageId", boardingPasses.get(0).getId());
        }

        // Fetch Hotel Bookings
        List<HotelBooking> hotelBookings = hotelBookingService.getBookingsForUser(username);
        System.out.println("DEBUG - HotelBookings: " + hotelBookings);
        model.addAttribute("bookings", hotelBookings);

        // Fetch Car Rentals
        List<CarRental> carRentals = carRentalService.getRentalsForUser(username);
        model.addAttribute("carRentals", carRentals);

        // Render Wallet page with all user-specific data
        return "Wallet";
    }


    //Quick code to enable to upload page to be uploaded
    @GetMapping("/upload")
    public String showUploadPage(Model model) {
        return "Upload";
    }

    //Quick code to get the userid by username
    @GetMapping("/getUserId")
    public Integer getUserIdByUsername(@RequestParam String username) {
        return userService.getUserIdByUsername(username);
    }

    //Quick code to access taxi page
    @GetMapping("/taxi")
    public String showTaxiPage() {
        return "Taxi";
    }

    //Quick code to access community page
    @GetMapping("/community")
    public String showCommunityPage() {
        return "Community";
    }

    //Quick code to access map page
    @GetMapping("/map")
    public String showMapPage() {
        return "Map";
    }

    //Quick code to access dashboard page
    @GetMapping("/dashboard")
    public String showDashboardPage() {
        return "Dashboard";
    }

    //Quick code to access about page
    @GetMapping("/about")
    public String showAboutPage() {
        return "About";
    }

    @GetMapping("/contact")
    public String showContactPage() {
        return "Contact";
    }

    @GetMapping("/avoid")
    public String showAvoidPage() {
        return "Avoid";
    }
}