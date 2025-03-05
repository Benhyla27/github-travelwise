package com.TravelWise.TravelWise.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "cities")
public class CityModel {

    // Auto generates the ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cityId;
    private String name;

    // Foreign key to the country table
    @ManyToOne
    @JoinColumn(name = "country_id", nullable = false)
    private CountryModel country;
    private String description;

    //Chatgpt to gave @jsonproperty annotation to fix display issue
    // Ensures the correct mapping when converting from json data
    @Column(name = "image_url")
    @JsonProperty("image_url")
    private String imageUrl;

    // //Chatgpt to gave @jsonproperty annotation to fix display issue
    // Ensures the correct mapping when converting from json data
    @JsonProperty("public_transport_info")
    @Column(name = "public_transport_info")

    private String publicTransportInfo;
    private String restaurants;
    private String activities;




    // Getters and Setters

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CountryModel getCountry() {
        return country;
    }

    public void setCountry(CountryModel country) {
        this.country = country;
    }

    public Long getCityId() {
        return cityId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getRestaurants() {
        return restaurants;
    }

    public void setRestaurants(String restaurants) {
        this.restaurants = restaurants;
    }

    public String getActivities() {
        return activities;
    }

    public void setActivities(String activities) {
        this.activities = activities;
    }

    public String getPublicTransportInfo() {
        return publicTransportInfo;
    }

    public void setPublicTransportInfo(String publicTransportInfo) {
        this.publicTransportInfo = publicTransportInfo;
    }

}




