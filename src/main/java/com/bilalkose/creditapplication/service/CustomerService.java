package com.bilalkose.creditapplication.service;

import com.bilalkose.creditapplication.dto.CustomerDto;
import com.bilalkose.creditapplication.dto.request.CreateCustomerRequest;
import com.bilalkose.creditapplication.model.Customer;

public interface CustomerService {

    CustomerDto save(CreateCustomerRequest createCustomerRequest);
    CustomerDto update(CustomerDto customerDto);
    CustomerDto getById(Long id);

    Customer getByIdForBusiness(Long id);
    void delete(Long id);
}
