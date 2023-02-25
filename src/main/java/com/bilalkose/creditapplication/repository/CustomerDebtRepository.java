package com.bilalkose.creditapplication.repository;

import com.bilalkose.creditapplication.model.CustomerDebt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerDebtRepository extends JpaRepository<CustomerDebt,Long> {

    List<CustomerDebt> findAllByCustomer_CitizenshipNumber(String citizenshipNumber);
}
