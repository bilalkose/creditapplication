package com.bilalkose.creditapplication.repository;

import com.bilalkose.creditapplication.dto.request.CreateCustomerRequest;
import com.bilalkose.creditapplication.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer,Long> {

    void deleteById(Long id);

    boolean existsById(Long id);

    boolean existsByCitizenshipNumber(String citizenShipNumber);
}
