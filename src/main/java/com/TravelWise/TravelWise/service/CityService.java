package com.TravelWise.TravelWise.service;

import com.TravelWise.TravelWise.model.CityModel;
import com.TravelWise.TravelWise.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

// Inspiration - https://stackoverflow.com/questions/72176668/using-optionals-correctly-in-service-layer-of-spring-boot-application -
// https://www.youtube.com/watch?v=UhSEsoIUROA -

@Service
public class CityService {
    // Injects the City repository to manage city data
    @Autowired
    private CityRepository cityRepository;

    // This method is used to get all the cities in a country
    public List<CityModel> getCitiesByCountry(Long countryId) {
        return cityRepository.findByCountry_CountryId(countryId);
    }
    // This method is used to get a city by its ID
    public CityModel getCityById(Long cityId) {
        return cityRepository.findById(cityId)
                .orElseThrow(() -> new IllegalArgumentException("City not found with ID: " + cityId));
    }
}