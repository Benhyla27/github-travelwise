package com.TravelWise.TravelWise.controller;

import com.TravelWise.TravelWise.model.BoardingPass;
import com.TravelWise.TravelWise.repository.BoardingPassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

// When asking why i was having issues chatgpt recommended an imagecontroller
//Co-pilot gave some code to help me with the image controller and I adapted to my needs

@Controller
public class ImageController {

    //Injects repository
    @Autowired
    private BoardingPassRepository boardingPassRepository;

    //Searches the database for the image
    // If the image is found, it returns the image
    @GetMapping("/image/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable Long id) {
        Optional<BoardingPass> boardingPassOpt = boardingPassRepository.findById(id);

        //If it exists it get retrieved and returned with the correct headers and data type
        if (boardingPassOpt.isPresent()) {
            BoardingPass boardingPass = boardingPassOpt.get();
            byte[] imageBytes = boardingPass.getFileData();

            //Allows jpeg, png and pdf files to be downloaded
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + boardingPass.getFileName() + "\"")
                    .contentType(MediaType.IMAGE_JPEG)
                    .contentType(MediaType.IMAGE_PNG)
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(imageBytes);
        } else {
            //If not found gives error
            return ResponseEntity.notFound().build();
        }
    }
}