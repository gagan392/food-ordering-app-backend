package org.upgrad.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.upgrad.config.Constants;
import org.upgrad.models.Address;
import org.upgrad.models.States;
import org.upgrad.models.User;
import org.upgrad.models.UserAuthToken;
import org.upgrad.repositories.AddressRepository;
import org.upgrad.services.AddressService;
import org.upgrad.services.UserAuthTokenService;

import java.util.regex.Matcher;

@RestController
@RequestMapping("/api")
public class AddressController {

    @Autowired
    private AddressService addCRUD;

    @Autowired
    private UserAuthTokenService userAuthTokenService;



    @PostMapping("/address")
    @CrossOrigin
    //@RequestHeader String accessToken,
    public ResponseEntity<?> addAddress(@RequestHeader String accessToken,@RequestParam(value = "Locality", required = true) String locality,@RequestParam(value = "Flat_building_number", required = true) String flat_building_number ,@RequestParam(value = "City", required = true) String city , @RequestParam(value = "Zipcode", required = true) String zipcode ,@RequestParam String Type,@RequestParam(value = "State Id", required = true) int stateId){
        /*
        * User Authentication
        * */

        UserAuthToken userAuthToken = userAuthTokenService.isUserLoggedIn(accessToken);
        if (userAuthToken == null) {
            return new ResponseEntity<>("Please Login first to access this endpoint!", HttpStatus.UNAUTHORIZED);
        }

        if (userAuthToken.getLogoutAt() != null) {
            return new ResponseEntity<Object>("You have already logged out. Please Login first to access this endpoint!", HttpStatus.UNAUTHORIZED);
        }
        User user = userAuthToken.getUser();

        int loggedInUserId = user.getId();

        Matcher zipCode = Constants.VALID_ZIP_CODE.matcher(zipcode);
        if (!zipCode.matches()) {
            return new ResponseEntity<Object>("Invalid zip code!", HttpStatus.BAD_REQUEST);
        }
        //State ID has to be valid
        if (stateId<0 && stateId > 36) {
            return new ResponseEntity<Object>("Invalid State Id!", HttpStatus.BAD_REQUEST);
        }



        Address currAddress = addCRUD.addAddress(locality,flat_building_number ,city ,zipcode,stateId);
        System.out.println("Current address id is "+currAddress.getId());


        //Now need to add the two information in the user_address table.
        addCRUD.updateUserAddressOnNewAddressInsert(loggedInUserId,currAddress.getId(),Type);
        return new ResponseEntity<Object>("Address has been updated successfully!", HttpStatus.OK);

    }

    @GetMapping("/address/user")
    @CrossOrigin
    public Iterable<Address> getAllPermanentAddresses(@RequestHeader String accessToken){



            UserAuthToken userAuthToken = userAuthTokenService.isUserLoggedIn(accessToken);
        if (userAuthToken == null) {
            //return new ResponseEntity<>("Please Login first to access this endpoint!", HttpStatus.UNAUTHORIZED);
        }

        if (userAuthToken.getLogoutAt() != null) {
            //return new ResponseEntity<>("You have already logged out. Please Login first to access this endpoint!", HttpStatus.UNAUTHORIZED);
        }


        return addCRUD.getAllPermAddressForUser("tset");

    }


    @PutMapping("/address/")
    @CrossOrigin
    public ResponseEntity<?> updatePermanentAddress(@RequestHeader String accessToken,@RequestParam(value = "Flat_Building_Number", required = false) String Flat_Building_Number,@RequestParam(value = "Locality", required = false)  String Locality,@RequestParam(value = "City", required = false)  String City,@RequestParam(value = "Zip_Code", required = false)  String Zip_Code,@RequestParam(value = "State_Id", required = false)  int State_Id,@RequestParam(value = "Address Id", required = true) int addId){


            UserAuthToken userAuthToken = userAuthTokenService.isUserLoggedIn(accessToken);
        if (userAuthToken == null) {
            return new ResponseEntity<>("Please Login first to access this endpoint!", HttpStatus.UNAUTHORIZED);
        }

        if (userAuthToken.getLogoutAt() != null) {
            return new ResponseEntity<Object>("You have already logged out. Please Login first to access this endpoint!", HttpStatus.UNAUTHORIZED);
        }

        if (addCRUD.findAllAdressById(addId).size()==0) {
            return new ResponseEntity<Object>("No address with this address id!", HttpStatus.BAD_REQUEST);
        }
        //Now start the update function

        addCRUD.updatePermAddressForUser(Flat_Building_Number,Locality,City,Zip_Code,State_Id,addId);

        return new ResponseEntity<Object>("Address has been updated successfully!", HttpStatus.OK);
    }

    @DeleteMapping("/address/")
    @CrossOrigin
    public ResponseEntity<?> deletePermanentAddress(@RequestHeader String accessToken,@RequestParam(value = "Address Id", required = true) int addId){


            UserAuthToken userAuthToken = userAuthTokenService.isUserLoggedIn(accessToken);
        if (userAuthToken == null) {
            return new ResponseEntity<>("Please Login first to access this endpoint!", HttpStatus.UNAUTHORIZED);
        }

        if (userAuthToken.getLogoutAt() != null) {
            return new ResponseEntity<Object>("You have already logged out. Please Login first to access this endpoint!", HttpStatus.UNAUTHORIZED);
        }


        if (addCRUD.findAllAdressById(addId).size()==0) {
            return new ResponseEntity<Object>("No address with this address id!", HttpStatus.BAD_REQUEST);
        }
        addCRUD.deletePermAddressForUser(addId);
        return new ResponseEntity<Object>("Address has been deleted successfully!", HttpStatus.OK);
    }
    @GetMapping("/states}")
    @CrossOrigin
    public Iterable<States> getAllStates(){

        return addCRUD.getAllStates();
    }

    private ResponseEntity<?> checkAuthentication(UserAuthToken userAuthToken){
        //String accessToken = "someValue";
        //UserAuthToken userAuthToken = userAuthTokenService.isUserLoggedIn(accessToken);
        if (userAuthToken == null) {
            return new ResponseEntity<>("Please Login first to access this endpoint!", HttpStatus.UNAUTHORIZED);
        }

        if (userAuthToken.getLogoutAt() != null) {
            return new ResponseEntity<Object>("You have already logged out. Please Login first to access this endpoint!", HttpStatus.UNAUTHORIZED);
        }

        //User user = userAuthToken.getUser();

        return null;

    }


}
