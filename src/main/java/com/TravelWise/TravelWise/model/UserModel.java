package com.TravelWise.TravelWise.model;

import jakarta.persistence.*;
import java.util.Objects;

/**
 * This class represents the user accounts being created in the system.
 * Which is being connected and stored in the user_accounts table in the database.
 *
 * https://www.youtube.com/watch?v=x_nfnVU0wAI
 * This form I got from youtube, which I used to create the user account model.
 */
@Entity
@Table(name = "user_accounts")
public class UserModel {

    // Primary key field for unique user identification.
    //Auto generates the userid for each user account created.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userid;
    // Additional unique identifier for user

    // Fields in the table for storing the username, password, and email of a user. Which are filled out by the user
    private String username;
    private String password;
    private String email;

    // Getters and Setters created by IntelliJ IDEA for me
    public Integer getUserid() {
        return userid;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    // This checks if the model and account are the same
    // If they are the same, it returns true
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserModel userModel = (UserModel) o;
        return Objects.equals(userid, userModel.userid) &&
                Objects.equals(username, userModel.username) &&
                Objects.equals(password, userModel.password) &&
                Objects.equals(email, userModel.email);
    }


    //This creates a readable description of the user account
    //Used for debugging during the development issues I was facing
    @Override
    public String toString() {
        return "UserModel{" +
                "userid=" + userid +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                '}';
    }


    //This creates a hash code for the user account

    @Override
    public int hashCode() {
        return Objects.hash(userid, username, password, email);
    }
}