package com.bilalkose.creditapplication.service;

import com.bilalkose.creditapplication.model.CustomerDebt;

import java.util.List;

public interface CustomerDebtService {

    List<CustomerDebt> getCustomerDebtsByCitizenshipNumber(String citizenshipNumber);

    CustomerDebt save(CustomerDebt customerDebt);

    CustomerDebt createCustomerDebtForSave(Double debtAmount,long customerId);

}
