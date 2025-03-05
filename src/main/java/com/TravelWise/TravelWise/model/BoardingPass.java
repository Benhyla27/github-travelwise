package com.TravelWise.TravelWise.model;

import jakarta.persistence.*;

@Entity
@Table(name = "boarding_passes")
public class BoardingPass {

    //Primary key for the boarding pass table to be auto generated
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //Co-pilot code for @Joincolumn annotation
    //Foreign key to the user table
    //Changed from String username to UserModel user
    //This is to ensure that the username is a valid username in the user table
    @ManyToOne
    @JoinColumn(name = "username", referencedColumnName = "username", nullable = false)
    private UserModel user;  // Changed from String username to UserModel user

    //Stores name of file
    //Sets content of file to byte array
    private String fileName;
    private byte[] fileData;

    // Getters and setters created by intellij
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public byte[] getFileData() {
        return fileData;
    }

    public void setFileData(byte[] fileData) {
        this.fileData = fileData;
    }
}