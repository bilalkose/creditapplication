package com.bilalkose.creditapplication.dto.converter;

import com.bilalkose.creditapplication.dto.CustomerDto;
import com.bilalkose.creditapplication.dto.request.CreateCustomerRequest;
import com.bilalkose.creditapplication.model.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerDtoConverter {
    public CustomerDto convert(Customer from) {
        return new CustomerDto(
                from.getId(),
                from.getCitizenshipNumber(),
                from.getName(),
                from.getSurname(),
                from.getMonthlyIncome(),
                from.getPhoneNumber(),
                from.getBirthday(),
                from.getCreditScore(),
                from.getCustomerDebts());
    }

    public Customer convert(CustomerDto from) {
        return Customer.builder()
                .id(from.getId())
                .citizenshipNumber(from.getCitizenshipNumber())
                .phoneNumber(from.getPhoneNumber())
                .name(from.getName()).surname(from.getSurname())
                .phoneNumber(from.getPhoneNumber())
                .creditScore(from.getCreditScore())
                .monthlyIncome(from.getMonthlyIncome())
                .birthday(from.getBirthday())
                .build();
    }

    public Customer convert(CreateCustomerRequest from) {
        return Customer.builder().name(from.getName()).surname(from.getSurname()).phoneNumber(from.getPhoneNumber()).citizenshipNumber(from.getCitizenshipNumber()).monthlyIncome(from.getMonthlyIncome()).birthday(from.getBirthday()).build();
    }

}
