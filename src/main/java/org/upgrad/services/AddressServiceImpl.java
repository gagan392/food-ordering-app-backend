package org.upgrad.services;


import org.springframework.stereotype.Service;
import org.upgrad.models.Address;
import org.upgrad.models.States;
import org.upgrad.models.UserAddress;
import org.upgrad.repositories.AddressRepository;
import org.upgrad.repositories.StateRepository;
import org.upgrad.repositories.UserAddressRepository;
import java.util.List;

import javax.transaction.Transactional;

@Service
@Transactional
public class AddressServiceImpl implements AddressService{

    private final AddressRepository addRepo;
    private final StateRepository stateRepo;
    private final UserAddressRepository userAddRepo;

    public AddressServiceImpl(AddressRepository addRepo, StateRepository stateRepo,UserAddressRepository userAddRepo) {
        this.addRepo = addRepo;
        this.stateRepo = stateRepo;
        this.userAddRepo = userAddRepo;
    }


    @Override
    public Address addAddress(String locality,String flat_building_number ,String city ,  String zipcode, int stateId) {
        States curState =  stateRepo.findStateById(stateId);
        Address addNew = new Address(locality,flat_building_number ,city ,zipcode,curState);
        //The save method is given by JPA hence need not be specifically written
        System.out.printf("[ADDRESS SERVICE] Details from the request - %s , %s , %s , %s , %s\n",locality,flat_building_number ,city ,zipcode,curState.getStateName());
        return addRepo.save(addNew);

    }

    @Override
    public Iterable<Address> getAllPermAddressForUser(String accessToken) {
        accessToken = "";
        return addRepo.getAllPermAddByUser(1);
    }

    @Override
    public boolean updatePermAddressForUser(int addressId) {
        return false;
    }

    public void deletePermAddressForUser(int addressId) {
        addRepo.deleteById(addressId);
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

        userAddRepo.save(currUserToAdd);

    }
    @Override
    public List<Address> findAllAdressById(int pId){
        List<Address> allAddress = addRepo.findAddressByGivenId(pId);
        return allAddress;
    }
}
