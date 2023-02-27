package com.bilalkose.creditapplication.service;

import com.bilalkose.creditapplication.dto.CustomerDto;
import com.bilalkose.creditapplication.dto.request.CreateCustomerRequest;
import com.bilalkose.creditapplication.dto.request.GetCustomerCreditApplicationRequest;

public interface CustomerService {

    CustomerDto save(CreateCustomerRequest createCustomerRequest);

    CustomerDto update(CustomerDto customerDto);

    CustomerDto getById(Long id);

    void delete(Long id);

    CustomerDto getCustomerByCitizenshipNumberAndBirthDate(GetCustomerCreditApplicationRequest getCustomerCreditApplicationRequest);
}
