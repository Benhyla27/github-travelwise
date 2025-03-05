package com.TravelWise.TravelWise.service;

import com.TravelWise.TravelWise.model.UserModel;
import com.TravelWise.TravelWise.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.persistence.EntityManager;

@Service
public class UserService {

// https://www.youtube.com/watch?v=x_nfnVU0wAI took from this youtube video for my userservice

    //Had Autowired but recommended private finale instead by Intellij
    //Used to inject userrepository and entitymanager
    private final UserRepository userRepository;
    private final EntityManager entityManager;


//Inject these dependencies into the constructor
    //Both used to interact with the database
    @Autowired
    public UserService(UserRepository userRepository, EntityManager entityManager) {
        this.userRepository = userRepository;
        this.entityManager = entityManager;
    }

//This method registers a new user in the system
    //It checks the input values and if they're null it fails registration
    //If they're valid a new user object is created and saved to the database
    @Transactional
    public UserModel registerUser(String username, String password, String email) {
        if (username == null || password == null || email == null) {
            return null; // Return null on invalid input
        } else {
            UserModel userModel = new UserModel();
            userModel.setUsername(username);
            userModel.setPassword(password);
            userModel.setEmail(email);
            UserModel savedUser = userRepository.save(userModel);
            entityManager.flush(); // Ensure changes are flushed to the database

            //If the user id was assigned correctly it returns the user, otherwise gives and error
            if (savedUser.getUserid() != null) {
                return savedUser;
            } else {
                throw new RuntimeException("User ID assignment failed on registration.");
            }
        }
    }

    //This method authenticates a user by checking the input values against the database
    @Transactional(readOnly = true)
    public UserModel authenticate(Integer userId, String password) {
        if (userId == null || password == null) {
            return null; // Return null if input is invalid
        }

        //This was used by me to print out the authenticated user id and password to the console for debugging
        System.out.println("Authenticating user with userId: " + userId + " and password: " + password);

        //This finds the user by the user id iin database
        UserModel user = userRepository.findById(userId).orElse(null);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        } else {
            System.out.println("Authentication failed for userId: " + userId);
            return null;
        }
    }

    //This method finds the user id by the username in the database
    //Loos for username in the repositroy
    @Transactional(readOnly = true)
    public Integer getUserIdByUsername(String username) {
        UserModel user = userRepository.findByUsername(username).orElse(null);
        if (user != null) {
            return user.getUserid();
        } else {
            return null;
        }
    }
}