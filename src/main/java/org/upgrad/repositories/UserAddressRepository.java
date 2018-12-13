package org.upgrad.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.upgrad.models.Address;
import org.upgrad.models.Restaurant;
import org.upgrad.models.UserAddress;

import java.util.List;

public interface UserAddressRepository extends CrudRepository<UserAddress, Integer> {


}
