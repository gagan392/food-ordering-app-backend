package org.upgrad.repositories;

import org.springframework.data.repository.CrudRepository;
import org.upgrad.models.Address;
import org.upgrad.models.UserAddress;

public interface UserAddressRepository extends CrudRepository<UserAddress, Integer> {

}
