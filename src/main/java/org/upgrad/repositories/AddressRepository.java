package org.upgrad.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.upgrad.models.Address;


import javax.transaction.Transactional;
import java.util.List;


@Repository
public interface AddressRepository extends CrudRepository<Address, Integer> {
    @Query(nativeQuery = true,value="SELECT * FROM address WHERE id=?1")
    Address findAddressById(int id);

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

    @Transactional
    @Modifying
    @Query(nativeQuery = true,value="update address set flat_buil_number=?1,locality=?2,city=?3,zipcode=?4,state_id=?5  where id=?6")
    void editAddressValues(String flat_buil_number,String locality,String city,String zipcode,int state_id ,int id);

}
