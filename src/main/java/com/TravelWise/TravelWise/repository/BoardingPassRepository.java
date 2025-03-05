package com.TravelWise.TravelWise.repository;

import com.TravelWise.TravelWise.model.BoardingPass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// Based off - https://www.youtube.com/watch?v=x_nfnVU0wAI
//Adapted for use in this project with username as the search parameter
@Repository
public interface BoardingPassRepository extends JpaRepository<BoardingPass, Long> {
    List<BoardingPass> findByUser_Username(String username);


}