package org.upgrad.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.upgrad.models.Address;


@Repository
public interface AddressRepository extends CrudRepository<Object, Integer> {
    @Query(nativeQuery = true,value="SELECT locality FROM address WHERE id=?1")
    String findAddressById(int id);
}
