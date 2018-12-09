package org.upgrad.repositories;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.upgrad.models.Address;
import org.upgrad.models.Restaurant;
import org.upgrad.models.User;

import java.util.List;

// This repository interface is responsible for the interaction between the user service with the user database

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    @Query(nativeQuery = true,value="SELECT PASSWORD FROM USERS WHERE contact_number=?1")
    String findUserPassword(String contactNumber);

    @Query(nativeQuery = true,value = "SELECT * FROM USERS WHERE contact_number=?1")
    User findUser(String contactNumber);

    @Query(nativeQuery = true, value = "SELECT USERS.ID, ADDRESS.flat_buil_number,ADDRESS.locality,ADDRESS.city," +
            "ADDRESS.zipcode,ADDRESS.state_id,STATES.STATE_NAME\n" +
            "FROM USERS\n" +
            "INNER JOIN ADDRESS\n" +
            "ON USERS.ID = ADDRESS.ID\n" +
            "INNER JOIN STATES\n" +
            "ON STATES.ID = ADDRESS.ID WHERE USERS.ID ?1")
    List<Address> getAll();
}

