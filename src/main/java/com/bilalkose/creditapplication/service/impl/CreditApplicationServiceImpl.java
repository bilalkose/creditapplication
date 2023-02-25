package com.bilalkose.creditapplication.service.impl;

import com.bilalkose.creditapplication.dto.CreditApplicationResultDto;
import com.bilalkose.creditapplication.dto.CustomerDto;
import com.bilalkose.creditapplication.enums.CreditApplicationResult;
import com.bilalkose.creditapplication.model.CreditApplication;
import com.bilalkose.creditapplication.model.Customer;
import com.bilalkose.creditapplication.repository.CreditApplicationRepository;
import com.bilalkose.creditapplication.service.CreditApplicationService;
import com.bilalkose.creditapplication.service.CreditScoreService;
import com.bilalkose.creditapplication.service.CustomerDebtService;
import org.springframework.stereotype.Service;

@Service
public class CreditApplicationServiceImpl implements CreditApplicationService {
    private final CreditApplicationRepository creditApplicationRepository;
    private final CustomerServiceImpl customerService;
    private final CustomerDebtService customerDebtService;
    private final CreditScoreService creditScoreService;

    private final int creditLimitMultiplier = 4;

    public CreditApplicationServiceImpl(CreditApplicationRepository creditApplicationRepository, CustomerServiceImpl customerService, CustomerDebtService customerDebtService, CreditScoreService creditScoreService) {
        this.creditApplicationRepository = creditApplicationRepository;
        this.customerService = customerService;
        this.customerDebtService = customerDebtService;
        this.creditScoreService = creditScoreService;
    }


    @Override
    public CreditApplicationResultDto save(Long customerId) {
        CustomerDto customerDto = this.customerService.getById(customerId);
        return this.calculateCreditAmount(customerDto);
    }


    private CreditApplicationResultDto calculateCreditAmount(CustomerDto customerDto) {
        double creditAmount = 0.0;
        if (customerDto.getCreditScore() < 500) {
            this.creditApplicationRepository.save(this.createCreditApplication(customerDto, creditAmount, CreditApplicationResult.REJECTED));
            return new CreditApplicationResultDto(CreditApplicationResult.REJECTED, creditAmount);
        } else if (customerDto.getCreditScore() > 500 && customerDto.getCreditScore() < 1000 && customerDto.getMonthlyIncome() < 5000) {
            creditAmount = 10000.0;
            return this.saveRequiredFieldsForSuccessApplicationResult(customerDto, creditAmount);
        } else if (customerDto.getCreditScore() > 500 && customerDto.getCreditScore() < 1000 && customerDto.getMonthlyIncome() > 5000 && customerDto.getMonthlyIncome() <= 10000) {
            creditAmount = 20000.0;
            return this.saveRequiredFieldsForSuccessApplicationResult(customerDto, creditAmount);
        } else if (customerDto.getCreditScore() > 500 && customerDto.getCreditScore() < 1000 && customerDto.getMonthlyIncome() > 10000) {
            creditAmount = customerDto.getMonthlyIncome() * this.creditLimitMultiplier / 2;
            return this.saveRequiredFieldsForSuccessApplicationResult(customerDto, creditAmount);
        } else if (customerDto.getCreditScore() >= 1000) {
            creditAmount = customerDto.getMonthlyIncome() * this.creditLimitMultiplier;
            return this.saveRequiredFieldsForSuccessApplicationResult(customerDto, creditAmount);
        }
        return new CreditApplicationResultDto(CreditApplicationResult.REJECTED, creditAmount);
    }

    private CreditApplication createCreditApplication(CustomerDto customerDto, Double creditAmount, CreditApplicationResult creditApplicationResult) {
        return CreditApplication.builder().creditApplicationResult(creditApplicationResult).creditAmount(creditAmount).customer(Customer.builder().id(customerDto.getId()).build()).build();
    }

    private CreditApplicationResultDto saveRequiredFieldsForSuccessApplicationResult(CustomerDto customerDto, double creditAmount) {
        this.creditApplicationRepository.save(this.createCreditApplication(customerDto, creditAmount, CreditApplicationResult.SUCCESS));
        this.customerDebtService.save(this.customerDebtService.createCustomerDebtForSave(creditAmount, customerDto.getId()));
        this.creditScoreService.calculateCreditScoreForNewDebt(customerDto.getId());
        return new CreditApplicationResultDto(CreditApplicationResult.SUCCESS, creditAmount);
    }

}
