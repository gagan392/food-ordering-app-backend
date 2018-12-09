package org.upgrad.services;

import com.sun.tools.javac.util.List;
import org.springframework.stereotype.Service;
import org.upgrad.models.Address;
import org.upgrad.models.States;


public interface AddressService {

    //EndPoint#1 Add Address
    Address addAddress(String locality,String flat_building_number ,String city ,  String zipcode, int stateId);

    //EndPoint#2 Get All Permanent Addresses
    Iterable<Address> getAllPermAddressForUser(String accessToken);

    //EndPoint#3 Update Permanent Address
    boolean updatePermAddressForUser(int addressId);

    //EndPoint#4 Delete Permanent Address
    boolean deletePermAddressForUser(int addressId);

    //EndPoint#5 Get All States
    Iterable<States> getAllStates();

    void updateUserAddressOnNewAddressInsert(String userID,String addressId,String type);

}
