package com.bilalkose.creditapplication.repository;

import com.bilalkose.creditapplication.model.CreditApplication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditApplicationRepository extends JpaRepository<CreditApplication,Long> {

}
