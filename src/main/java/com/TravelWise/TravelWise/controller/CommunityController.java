package com.TravelWise.TravelWise.controller;

import com.TravelWise.TravelWise.model.CityModel;
import com.TravelWise.TravelWise.model.CountryModel;
import com.TravelWise.TravelWise.service.CityService;
import com.TravelWise.TravelWise.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


//https://docs.spring.io/spring-boot/index.html - Spring Boot API Guide
//CommunityController is responsible for handling API requests related to countries and cities.
//It provides endpoints to retrieve all countries, cities within a country, and details about a specific city.
//The controller uses CountryService and CityService to fetch data from the database.
@RestController
@RequestMapping("/community")
public class CommunityController {

    //Inject city & country service
    @Autowired
    private CountryService countryService;
    @Autowired
    private CityService cityService;

//Retrieves a list of all availible countries
    //Uses the API endpoint GET /community/countries
    //Calls CountryService
    @GetMapping("/countries")
    public List<CountryModel> getAllCountries() {
        return countryService.getAllCountries();
    }

    //Retrieves a list of all cities within a country
    //Uses the API endpoint GET /community/cities/{countryId}
    //Calls CityService
    @GetMapping("/cities/{countryId}")
    public List<CityModel> getCitiesByCountry(@PathVariable Long countryId) {
        return cityService.getCitiesByCountry(countryId);
    }
//Retrieves details about a specific city e.g. the data stored in the database
    //Uses the API endpoint GET /community/city-details/{cityId}
    //Calls CityService
    @GetMapping("/city-details/{cityId}")
    public CityModel getCityDetails(@PathVariable Long cityId) {
        return cityService.getCityById(cityId);
    }



}
