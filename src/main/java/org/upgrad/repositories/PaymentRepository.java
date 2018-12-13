package org.upgrad.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.upgrad.models.Payment;

import java.util.List;
/**
 * This repository interface is responsible for the interaction between the Payment service with the Payment database
 */
@Repository
public interface PaymentRepository extends CrudRepository<Payment, Integer> {

    @Query(nativeQuery = true,  value = "SELECT * FROM payment")
    List<Payment> fetchPaymentMethod();

}