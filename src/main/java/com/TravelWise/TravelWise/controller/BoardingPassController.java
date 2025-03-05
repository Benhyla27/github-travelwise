package com.TravelWise.TravelWise.controller;

import com.TravelWise.TravelWise.model.BoardingPass;
import com.TravelWise.TravelWise.model.UserModel;
import com.TravelWise.TravelWise.repository.BoardingPassRepository;
import com.TravelWise.TravelWise.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

// For this form took inspiration from - https://www.youtube.com/watch?v=dX3ReJKG-Ek - how to add file upload to spring boot
// and https://www.youtube.com/watch?v=x_nfnVU0wAI - how to upload and display image in spring boot
// Help of co-pilot
// Stack overflow - https://stackoverflow.com/questions/67019469/how-to-upload-and-display-image-in-spring-boot - how to upload and display image in spring boot
// Adapted it all around what worked for me

//

@Controller
@RequestMapping("/boardingPass")
public class BoardingPassController {


    // Interacts with database for boarding pass related actions
    @Autowired
    private BoardingPassRepository boardingPassRepository;
// Interacts with database for user related actions
    @Autowired
    private UserRepository userRepository;

    // This handles the get request for the upload form returns the name of the template
    @GetMapping("/upload")
    public String showUploadForm() {
        return "Wallet";
    }

// This handles the postrequest for the upload form
    //It processes the file and saves it to the database with the username as the unique identifier for each user
    @PostMapping("/upload")
    //Retireves username from the session
    public String uploadBoardingPass(@RequestParam("file") MultipartFile file, HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username == null || username.isEmpty()) {

            return "redirect:/login";  // Ensure this is the actual login page URL
        }

        //Calls the saveBoardingPass method to save the file to the database
        //After successful upload redirects to the wallet page with the username staying in the session
        try {
            saveBoardingPass(username, file);
            return "redirect:/Wallet?Username=" + username;
        } catch (IOException e) {

            return "error_page";
        }
    }

    // This method saves the boarding pass to the database and is called above
    @PostMapping("/save")
    public void saveBoardingPass(@RequestParam("username") String username, @RequestParam("file") MultipartFile file) throws IOException {
        //Checks if the username is provided in the session
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Username is required");
        }
// Looks up user by username as unique identifier
        Optional<UserModel> userOptional = userRepository.findByUsername(username);
        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("User not found: " + username);
        }
        //Retrieves saved user and stores file, data to bytea,
        // links to boarding pass to user by username and stores file name
        UserModel user = userOptional.get();
        BoardingPass boardingPass = new BoardingPass();
        boardingPass.setFileData(file.getBytes());
        boardingPass.setUser(user);
        boardingPass.setFileName(file.getOriginalFilename());

        boardingPassRepository.save(boardingPass);
    }

    //Gets the image from the database and displays it on the wallet page
    //The image is retrieved by the id of the image
    @GetMapping("/boardingPass/image/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable Long id) {
        Optional<BoardingPass> boardingPassOptional = boardingPassRepository.findById(id);
        if (boardingPassOptional.isPresent()) {
            BoardingPass boardingPass = boardingPassOptional.get();
            byte[] imageData = boardingPass.getFileData();
            return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(imageData);
        } else {
            //if not found returns error
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/all")
    public String getAllBoardingPasses(HttpSession session, Model model) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return "redirect:/login"; // Redirect to login if user is not authenticated
        }

        // Retrieve all the boarding passes for the logged-in user
        List<BoardingPass> boardingPasses = boardingPassRepository.findByUser_Username(username);

        // Add the boarding passes to the model
        model.addAttribute("boardingPasses", boardingPasses);

        return "BoardingPass"; // Return the BoardingPass HTML template
    }

}