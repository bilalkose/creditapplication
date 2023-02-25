package com.bilalkose.creditapplication.service.impl;

import com.bilalkose.creditapplication.dto.CustomerDto;
import com.bilalkose.creditapplication.model.Customer;
import com.bilalkose.creditapplication.model.CustomerDebt;
import com.bilalkose.creditapplication.service.CreditScoreService;
import com.bilalkose.creditapplication.service.CustomerService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CreditScoreServiceImpl implements CreditScoreService {
    private final CustomerService customerService;

    private final int creditScoreVariable = 5;

    public CreditScoreServiceImpl(@Lazy CustomerService customerService) {
        this.customerService = customerService;
    }

    @Override
    public int calculateCreditScoreForCustomerSave(Customer customer) {
        return customer.getMonthlyIncome()/this.creditScoreVariable;
    }

    public void calculateCreditScoreForNewDebt(long customerId){
        CustomerDto customerDto = this.customerService.getById(customerId);
        double totalDebt = this.calculateTotalDebtForCustomer(customerDto.getCustomerDebts());
        int deptVariable = (int) (totalDebt *  this.creditScoreVariable * 10 / (customerDto.getMonthlyIncome()));
        int newCreditScore =  customerDto.getCreditScore() - deptVariable;
        customerDto.setCreditScore(newCreditScore > 0 ? newCreditScore : 0);
        this.customerService.update(customerDto);
    }

    private double calculateTotalDebtForCustomer(List<CustomerDebt> customerDebts){
        double total = 0.0;
        for(CustomerDebt customerDebt:customerDebts){
            total+=customerDebt.getDebtAmount();
        }
        return total;
    }
}
