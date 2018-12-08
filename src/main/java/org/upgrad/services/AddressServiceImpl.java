package org.upgrad.services;

import org.springframework.stereotype.Service;
import org.upgrad.models.Address;
import org.upgrad.models.States;
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
    public String getOneAddressForId(int id) {
        return addRepo.findAddressById(id);
    }


    public Iterable<Address> getAll() {
        return addRepo.findAll();
    }

    @Override
    public Iterable<States> getAllStates(){
        return stateRepo.findAll();
    }


}
