package org.upgrad.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.upgrad.models.Address;


import java.util.List;


@Repository
public interface AddressRepository extends CrudRepository<Address, Integer> {
    @Query(nativeQuery = true,value="SELECT locality FROM address WHERE id=?1")
    String findAddressById(int id);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "INSERT INTO ADDRESS(flat_buil_number, locality, city, zipcode, state_id) VALUES(?1,?2,?3,?4,?5)")
    void addAddress(String flatBuilNumber, String locality, String city, String zipcode, Integer stateId);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "INSERT INTO USER_ADDRESS(type, user_id, address_id) VALUES(?1,?2,?3)")
    void userAddressMapping(String type, Integer userId, Integer addressId);

    @Query(nativeQuery = true, value = "SELECT ID FROM ADDRESS ORDER BY ID DESC LIMIT 1")
    int findIdForLatestAddress();

    @Query(nativeQuery = true,value="SELECT * FROM address WHERE id=?1")
    List<Address> findAddressByGivenId(int id);

    @Query(nativeQuery = true, value = "SELECT USERS.ID, ADDRESS.flat_buil_number,ADDRESS.locality,ADDRESS.city," +
            "ADDRESS.zipcode,ADDRESS.state_id,STATES.STATE_NAME\n" +
            "FROM USERS\n" +
            "INNER JOIN ADDRESS\n" +
            "ON USERS.ID = ADDRESS.ID\n" +
            "INNER JOIN STATES\n" +
            "ON STATES.ID = ADDRESS.ID WHERE USERS.ID=?1")
    List<Address> getAllPermAddByUser(int id);

}
