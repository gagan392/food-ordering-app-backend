package org.upgrad.repositories;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.upgrad.models.User;

// This repository interface is responsible for the interaction between the user service with the user database

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    @Query(nativeQuery = true,value="SELECT PASSWORD FROM USERS WHERE contact_number=?1")
    String findUserPassword(String contactNumber);

    @Query(nativeQuery = true,value = "SELECT * FROM USERS WHERE contact_number=?1")
    User findUser(String contactNumber);
}

