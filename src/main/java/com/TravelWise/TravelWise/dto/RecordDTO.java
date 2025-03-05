package com.TravelWise.TravelWise.dto;

// https://stackoverflow.com/questions/63712187/java-records-jep359-as-spring-controller-request-and-response-dtos?
// ChatGPT told me I needed a DTO class to handle the data transfer between the client and the server, created from the above discussion
// This class is used to transfer record data between the client and the server
public class RecordDTO {

    private String recordType;
    private String recordDetails;

    // Constructor
    public RecordDTO(String recordType, String recordDetails) {
        this.recordType = recordType;
        this.recordDetails = recordDetails;
    }

    // Getters and Setters
    public String getRecordType() {
        return recordType;
    }

    public void setRecordType(String recordType) {
        this.recordType = recordType;
    }

    public String getRecordDetails() {
        return recordDetails;
    }

    public void setRecordDetails(String recordDetails) {
        this.recordDetails = recordDetails;
    }
}