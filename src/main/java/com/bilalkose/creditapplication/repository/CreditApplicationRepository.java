package com.bilalkose.creditapplication.repository;

import com.bilalkose.creditapplication.model.CreditApplication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CreditApplicationRepository extends JpaRepository<CreditApplication,Long> {

    List<CreditApplication> findAllByCustomer_BirthdayAndCustomer_CitizenshipNumber(LocalDate birthDay, String citizenshipNumber);
}
