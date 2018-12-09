package org.upgrad.services;

import org.springframework.stereotype.Service;
import org.upgrad.models.Address;
import org.upgrad.models.States;
import org.upgrad.models.UserAddress;
import org.upgrad.repositories.AddressRepository;
import org.upgrad.repositories.StateRepository;

import javax.transaction.Transactional;

@Service
@Transactional
public class AddressServiceImpl implements AddressService{

    private final AddressRepository addRepo;
    private final StateRepository stateRepo;

    public AddressServiceImpl(AddressRepository addRepo, StateRepository stateRepo) {
        this.addRepo = addRepo;
        this.stateRepo = stateRepo;
    }


    @Override
    public Address addAddress(String locality,String flat_building_number ,String city ,  String zipcode, int stateId) {
        Address addNew = new Address(locality,flat_building_number ,city ,zipcode,stateId);
        //The save method is given by JPA hence need not be specifically written
        System.out.printf("[ADDRESS SERVICE] Details from the request - %s , %s , %s , %s , %d\n",locality,flat_building_number ,city ,zipcode,stateId);
        return addRepo.save(addNew);

    }

    @Override
    public Iterable<Address> getAllPermAddressForUser(String accessToken) {
        return null;
    }

    @Override
    public boolean updatePermAddressForUser(int addressId) {
        return false;
    }

    @Override
    public boolean deletePermAddressForUser(int addressId) {
        return false;
    }

    @Override
    public Iterable<States> getAllStates(){
        return stateRepo.findAll();
    }

    @Override
    public void updateUserAddressOnNewAddressInsert(int userID,int addressId,String type){
        //When a new address was added it should be updated in the User_Address Table.
        System.out.printf("[updateUserAddressOnNewAddressInsert]");
        UserAddress currUserToAdd = new UserAddress(type,userID,addressId);

        addRepo.save(currUserToAdd);

    }
}
