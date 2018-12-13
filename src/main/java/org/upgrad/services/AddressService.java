package org.upgrad.services;
import org.springframework.stereotype.Service;
import org.upgrad.models.Address;
import org.upgrad.models.States;

import java.util.List;


public interface AddressService {

    //EndPoint#1 Add Address
    Address addAddress(String locality,String flat_building_number ,String city ,  String zipcode, int stateId);

    //EndPoint#2 Get All Permanent Addresses
    Iterable<Address> getAllPermAddressForUser(String accessToken);

    //EndPoint#3 Update Permanent Address
    void updatePermAddressForUser(String flat_buil_number,String locality,String city,String zipcode,int state_id ,int addressId);

    //EndPoint#4 Delete Permanent Address
    void deletePermAddressForUser(int addressId);

    //EndPoint#5 Get All States
    Iterable<States> getAllStates();

    //Helper Methods
    /* When a new address is inserted it has to linked in the User_Address table to maintain
    * Many To Many relation between User and Permanent Address
    * Thus the User_Address table gets an update when we add a new permanent or temp address
    * */
    void updateUserAddressOnNewAddressInsert(int userID,int addressId,String type);


    List<Address> findAllAdressById(int pId);
}
