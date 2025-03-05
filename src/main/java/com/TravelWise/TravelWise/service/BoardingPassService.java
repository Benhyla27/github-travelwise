package com.TravelWise.TravelWise.service;

import com.TravelWise.TravelWise.model.BoardingPass;
import com.TravelWise.TravelWise.model.UserModel;
import com.TravelWise.TravelWise.repository.BoardingPassRepository;
import com.TravelWise.TravelWise.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

// This service handles the logic for boarding pass operations.
//Inspriation - https://stackoverflow.com/questions/72176668/using-optionals-correctly-in-service-layer-of-spring-boot-application
// https://www.youtube.com/watch?v=UhSEsoIUROA


@Service
public class BoardingPassService {

    // Interacts with repository & database for boarding pass related actions
    @Autowired
    private BoardingPassRepository boardingPassRepository;

    // Injects the user repository to manage user data
    @Autowired
    private UserRepository userRepository;

    // Method to save the boarding pass to the database/ @Transactional annotation ensures the operation is atomic - recomended by ChatGPT
    @Transactional
    public void saveBoardingPass(String username, MultipartFile file) throws IOException {
        // Find the user by username
        Optional<UserModel> userOptional = userRepository.findByUsername(username);
        //If the username is not found throw an exception
        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("User not found: " + username);
        }
        // Get the user object
        UserModel user = userOptional.get();
        // Create a new boarding pass object
        BoardingPass boardingPass = new BoardingPass();
        boardingPass.setFileData(file.getBytes());
        boardingPass.setUser(user);
        boardingPass.setFileName(file.getOriginalFilename());

        //Saves the data to the database using the repository
        boardingPassRepository.save(boardingPass);
    }
    @Transactional(readOnly = true)
    public List<BoardingPass> getBoardingPassesForUser(String username) {
        return boardingPassRepository.findByUser_Username(username);
    }

}