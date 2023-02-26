package com.bilalkose.creditapplication.service.impl;

import com.bilalkose.creditapplication.dto.CreditApplicationDto;
import com.bilalkose.creditapplication.dto.CreditApplicationResultDto;
import com.bilalkose.creditapplication.dto.CustomerDto;
import com.bilalkose.creditapplication.dto.converter.CreditApplicationDtoConverter;
import com.bilalkose.creditapplication.dto.request.GetCustomerCreditApplicationRequest;
import com.bilalkose.creditapplication.enums.CreditApplicationResult;
import com.bilalkose.creditapplication.model.CreditApplication;
import com.bilalkose.creditapplication.model.Customer;
import com.bilalkose.creditapplication.repository.CreditApplicationRepository;
import com.bilalkose.creditapplication.service.CreditApplicationService;
import com.bilalkose.creditapplication.service.CreditScoreService;
import com.bilalkose.creditapplication.service.CustomerDebtService;
import com.bilalkose.creditapplication.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CreditApplicationServiceImpl implements CreditApplicationService {
    private final CreditApplicationRepository creditApplicationRepository;
    private final CustomerService customerService;
    private final CustomerDebtService customerDebtService;
    private final CreditScoreService creditScoreService;
    private final CreditApplicationDtoConverter creditApplicationDtoConverter;


    private final int creditLimitMultiplier = 4;

    public CreditApplicationServiceImpl(CreditApplicationRepository creditApplicationRepository, CustomerService customerService, CustomerDebtService customerDebtService, CreditScoreService creditScoreService, CreditApplicationDtoConverter creditApplicationDtoConverter) {
        this.creditApplicationRepository = creditApplicationRepository;
        this.customerService = customerService;
        this.customerDebtService = customerDebtService;
        this.creditScoreService = creditScoreService;
        this.creditApplicationDtoConverter = creditApplicationDtoConverter;
    }


    @Override
    public CreditApplicationResultDto save(GetCustomerCreditApplicationRequest getCustomerCreditApplicationRequest) {
        CustomerDto customerDto = this.customerService.getCustomerByCitizenshipNumberAndBirthDate(getCustomerCreditApplicationRequest);
        return this.calculateCreditAmount(customerDto);
    }



    @Override
    public List<CreditApplicationDto> getAllByCitizenshipNumberAndBirthDay(GetCustomerCreditApplicationRequest getCustomerCreditApplicationRequest) {
        List<CreditApplication> creditApplications =  this.creditApplicationRepository.findAllByCustomer_BirthdayAndCustomer_CitizenshipNumber(getCustomerCreditApplicationRequest.getBirthDay(),getCustomerCreditApplicationRequest.getCitizenshipNumber());
        return creditApplications.stream().map(creditApplication -> this.creditApplicationDtoConverter.convert(creditApplication)).collect(Collectors.toList());
    }


    private CreditApplicationResultDto calculateCreditAmount(CustomerDto customerDto) {
        double creditAmount = 0.0;
        if (customerDto.getCreditScore() < 500) {
            log.info("Info SMS: Credit Application : to:" + customerDto.getPhoneNumber() + " | " + CreditApplicationResult.REJECTED.toString() + " " +  customerDto.getCitizenshipNumber() + " / " +
                    customerDto.getName() + " " + customerDto.getSurname());
            this.creditApplicationRepository.save(this.createCreditApplication(customerDto, creditAmount, CreditApplicationResult.REJECTED));
            return new CreditApplicationResultDto(CreditApplicationResult.REJECTED, creditAmount);
        } else if (customerDto.getCreditScore() > 500 && customerDto.getCreditScore() < 1000 && customerDto.getMonthlyIncome() < 5000) {
            log.info("Info SMS: Credit Application : to:" + customerDto.getPhoneNumber() + " | " + CreditApplicationResult.SUCCESS.toString() + " " +  customerDto.getCitizenshipNumber() + " / " +
                    customerDto.getName() + " " + customerDto.getSurname());
            creditAmount = 10000.0;
            return this.saveRequiredFieldsForSuccessApplicationResult(customerDto, creditAmount);
        } else if (customerDto.getCreditScore() > 500 && customerDto.getCreditScore() < 1000 && customerDto.getMonthlyIncome() > 5000 && customerDto.getMonthlyIncome() <= 10000) {
            log.info("Info SMS: Credit Application : to:" + customerDto.getPhoneNumber() + " | " + CreditApplicationResult.SUCCESS.toString() + " " +  customerDto.getCitizenshipNumber() + " / " +
                    customerDto.getName() + " " + customerDto.getSurname());
            return this.saveRequiredFieldsForSuccessApplicationResult(customerDto, creditAmount);
        } else if (customerDto.getCreditScore() > 500 && customerDto.getCreditScore() < 1000 && customerDto.getMonthlyIncome() > 10000) {
            log.info("Info SMS: Credit Application : to:" + customerDto.getPhoneNumber() + " | " + CreditApplicationResult.SUCCESS.toString() + " " +  customerDto.getCitizenshipNumber() + " / " +
                    customerDto.getName() + " " + customerDto.getSurname());
            creditAmount = customerDto.getMonthlyIncome() * this.creditLimitMultiplier / 2;
            return this.saveRequiredFieldsForSuccessApplicationResult(customerDto, creditAmount);
        } else if (customerDto.getCreditScore() >= 1000) {
            log.info("Info SMS: Credit Application : to:" + customerDto.getPhoneNumber() + " | " + CreditApplicationResult.SUCCESS.toString() + " " +  customerDto.getCitizenshipNumber() + " / " +
                    customerDto.getName() + " " + customerDto.getSurname());
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
