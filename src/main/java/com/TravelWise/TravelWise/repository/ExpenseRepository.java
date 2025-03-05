package com.TravelWise.TravelWise.repository;

import com.TravelWise.TravelWise.model.ExpenseModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// Based off - https://www.youtube.com/watch?v=x_nfnVU0wAI
//Adapted for use in this project with username as the search parameter
// Repositroy for the database interactions for the expenses
public interface ExpenseRepository extends JpaRepository<ExpenseModel, Integer> {
    List<ExpenseModel> findByUsername(String username); // Fetch expenses by username
}