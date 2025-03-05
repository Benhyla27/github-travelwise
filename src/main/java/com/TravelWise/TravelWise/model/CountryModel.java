package com.TravelWise.TravelWise.model;

import jakarta.persistence.*;

@Entity
@Table(name = "countries")
public class CountryModel {

    // Auto generates the ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long countryId;


    private String name;

    // Getters and Setters
    public void setCountryId(Long countryId) {
        this.countryId = countryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCountryId() {
        return countryId;
    }
}
