package com.bilalkose.creditapplication.service.impl;

import com.bilalkose.creditapplication.model.Customer;
import com.bilalkose.creditapplication.model.CustomerDebt;
import com.bilalkose.creditapplication.repository.CustomerDebtRepository;
import com.bilalkose.creditapplication.service.CustomerDebtService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerDebtServiceImpl implements CustomerDebtService {

    private final CustomerDebtRepository customerDebtRepository;


    @Override
    public List<CustomerDebt> getCustomerDebtsByCitizenshipNumber(String citizenshipNumber) {
        return this.customerDebtRepository.findAllByCustomer_CitizenshipNumber(citizenshipNumber);
    }

    @Override
    public CustomerDebt save(CustomerDebt customerDebt) {
        return this.customerDebtRepository.save(customerDebt);
    }

    public CustomerDebt createCustomerDebtForSave(Double debtAmount,long customerId){
        return CustomerDebt.builder().debtAmount(debtAmount).customer(Customer.builder().id(customerId).build()).build();
    }
}
