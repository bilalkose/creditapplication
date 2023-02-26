package com.bilalkose.creditapplication.repository;

import com.bilalkose.creditapplication.dto.request.CreateCustomerRequest;
import com.bilalkose.creditapplication.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer,Long> {

    void deleteById(Long id);

    boolean existsById(Long id);

    boolean existsByCitizenshipNumber(String citizenShipNumber);

    Optional<Customer> findByCitizenshipNumberAndBirthday(String citizenshipNumber, LocalDate birthDay);
}
