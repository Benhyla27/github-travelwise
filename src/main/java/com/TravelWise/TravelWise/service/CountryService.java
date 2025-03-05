package com.TravelWise.TravelWise.service;

import com.TravelWise.TravelWise.model.CountryModel;
import com.TravelWise.TravelWise.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class CountryService {

    //Injects CountryRepository to manage country data
    @Autowired
    private CountryRepository countryRepository;

    // Method to get all countries and list them on the user end
    public List<CountryModel> getAllCountries() {
        return countryRepository.findAll();
    }
}
