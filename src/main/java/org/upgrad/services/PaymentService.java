package org.upgrad.services;

import org.upgrad.models.Payment;

import java.util.List;
/**
 * This PaymentService interface gives the service that exist in the payment service implementation
 * class.  Controller class will be calling the service methods by this interface.
 */
public interface PaymentService {
    List<Payment> getPaymentMethods();
}