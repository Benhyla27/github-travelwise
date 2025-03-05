package com.TravelWise.TravelWise.repository;

import com.TravelWise.TravelWise.model.UserModel;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


// Got bases from youtube video but adapted it so it uses username as the unique identifier
// This is the UserRepository interface. It provides a way to interact with the user accounts in the database.
@Repository
public interface UserRepository extends JpaRepository<UserModel, Integer> {
    Optional<UserModel> findByUsername(String username);
}

