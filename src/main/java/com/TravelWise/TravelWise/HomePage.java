package com.TravelWise.TravelWise;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class HomePage {

    @GetMapping("/welcome")
    public String homePage() {
        return "Welcome to TravelWise!";
    }
}
