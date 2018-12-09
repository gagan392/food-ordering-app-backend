package org.upgrad.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.upgrad.models.Address;
import org.upgrad.models.States;
import org.upgrad.models.User;
import org.upgrad.models.UserAuthToken;
import org.upgrad.repositories.AddressRepository;
import org.upgrad.services.AddressService;
import org.upgrad.services.UserAuthTokenService;

@RestController
@RequestMapping("/api")
public class AddressController {

    @Autowired
    private AddressService addCRUD;

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
    //@RequestHeader String accessToken,
    public ResponseEntity<?> addAddress(@RequestParam(value = "Locality", required = true) String locality,@RequestParam(value = "Flat_building_number", required = true) String flat_building_number ,@RequestParam(value = "City", required = true) String city , @RequestParam(value = "Zipcode", required = true) String zipcode ,@RequestParam String Type,@RequestParam(value = "State Id", required = true) int stateId){
        /*
        * User Authentication
        * */
        if(false){//Stub code


        String accessToken = "someValue";
        UserAuthToken userAuthToken = userAuthTokenService.isUserLoggedIn(accessToken);
        if (userAuthToken == null) {
            return new ResponseEntity<>("Please Login first to access this endpoint!", HttpStatus.UNAUTHORIZED);
        }

        if (userAuthToken.getLogoutAt() != null) {
            return new ResponseEntity<Object>("You have already logged out. Please Login first to access this endpoint!", HttpStatus.UNAUTHORIZED);
        }

        User user = userAuthToken.getUser();
        }//Stub code
        int loggedInUserId = 1;//user.getId();

        Address currAddress = addCRUD.addAddress(locality,flat_building_number ,city ,zipcode,stateId);
        System.out.println("Current address id is "+currAddress.getId());


        //Now need to add the two information in the user_address table.
        addCRUD.updateUserAddressOnNewAddressInsert(loggedInUserId,currAddress.getId(),Type);
        //return new ResponseEntity<Object>(mapRestaurantResponse(restaurant), HttpStatus.OK);
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
