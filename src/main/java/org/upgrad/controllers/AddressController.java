package org.upgrad.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.upgrad.models.Address;
import org.upgrad.models.States;
import org.upgrad.repositories.AddressRepository;
import org.upgrad.services.AddressService;
import org.upgrad.services.UserAuthTokenService;

@RestController
@RequestMapping("/api")
public class AddressController {

    @Autowired
    private AddressService addCRUD;

    @Autowired
    private AddressRepository addRepo;

    @Autowired
    private UserAuthTokenService userAuthTokenService;


    //Test Method
    @GetMapping("/address/")
    @CrossOrigin
    public ResponseEntity<?> getAddress(@RequestParam("pkID") int pkID){



        return null;
    }
    //End Test Method


    @PostMapping("/address")
    @CrossOrigin
    public ResponseEntity<?> addAddress(@RequestParam(value = "Locality", required = true) String locality,@RequestParam(value = "Flat_building_number", required = true) String flat_building_number ,@RequestParam(value = "City", required = true) String city , @RequestParam(value = "Zipcode", required = true) String zipcode ,@RequestParam String Type,@RequestParam(value = "State Id", required = true) int stateId){
        System.out.printf("[ADDRESS CONTROLLER] Details from the request - %s , %s , %s , %s , %d",locality,flat_building_number ,city ,zipcode,stateId);
        addCRUD.addAddress(locality,flat_building_number ,city ,zipcode,stateId);
        return null;
    }

    @GetMapping("/address/user")
    @CrossOrigin
    public Iterable<Address> getAllPermanentAddresses(){

        return null;

    }


    @PutMapping("/address/")
    @CrossOrigin
    public ResponseEntity<?> updatePermanentAddress(){

        return null;
    }

    @DeleteMapping("/address/")
    @CrossOrigin
    public ResponseEntity<?> deletePermanentAddress(){

        return null;
    }
    @GetMapping("/states}")
    @CrossOrigin
    public Iterable<States> getAllStates(){

        return addCRUD.getAllStates();
    }



}
