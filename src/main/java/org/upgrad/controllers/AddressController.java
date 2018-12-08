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
    public ResponseEntity<?> addAddress(){

        return null;
    }

    @GetMapping("/address/user")
    @CrossOrigin
    public Iterable<Address> getAllPermanentAddresses(){

        Iterable<Address> myVal = addCRUD.getAll();
        for(Address val:myVal){
            System.out.println(val);
        }

        return myVal;
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
