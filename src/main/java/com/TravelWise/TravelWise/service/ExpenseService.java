package com.TravelWise.TravelWise.service;

import com.TravelWise.TravelWise.model.ExpenseModel;
import com.TravelWise.TravelWise.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

// Inspiration - https://stackoverflow.com/questions/72176668/using-optionals-correctly-in-service-layer-of-spring-boot-application -
// https://www.youtube.com/watch?v=UhSEsoIUROA -
// Co-pilot created the implementation of this service class
@Service
public class ExpenseService {

    // Auto injects the expense repository to manage expense data
    @Autowired
    private ExpenseRepository expenseRepository;

    // This method saves the expense to the database
    public void saveExpense(ExpenseModel expense) {
        expenseRepository.save(expense);
    }

    // This method retrieves all the expenses for a user
    public List<ExpenseModel> getUserExpenses(String username) {
        return expenseRepository.findByUsername(username);
    }



}