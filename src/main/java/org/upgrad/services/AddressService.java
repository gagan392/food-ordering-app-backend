package org.upgrad.services;

import com.sun.tools.javac.util.List;
import org.springframework.stereotype.Service;
import org.upgrad.models.Address;
import org.upgrad.models.States;


public interface AddressService {

    //save method from jpa
    //public void addAddress(Address newAdd);

    //findById from jpa
    //public Address getAddress(int id);

    //findAll from jpa
    //public List<Address> listAllAddress();
    String getOneAddressForId(int id);

    public Iterable<Address> getAll();



    // Get All States
    public Iterable<States> getAllStates();

}
