package com.bilalkose.creditapplication.service;

import com.bilalkose.creditapplication.model.Customer;

public interface CreditScoreService {

    int calculateCreditScoreForCustomerSave(Customer customer);
    void calculateCreditScoreForNewDebt(long customerId);
}
