package com.bilalkose.creditapplication.service.impl;

import com.bilalkose.creditapplication.dto.CustomerDto;
import com.bilalkose.creditapplication.dto.converter.CustomerDtoConverter;
import com.bilalkose.creditapplication.dto.request.CreateCustomerRequest;
import com.bilalkose.creditapplication.dto.request.GetCustomerCreditApplicationRequest;
import com.bilalkose.creditapplication.exception.AlreadyCreatedCustomerException;
import com.bilalkose.creditapplication.exception.CustomerNotFoundException;
import com.bilalkose.creditapplication.model.Customer;
import com.bilalkose.creditapplication.repository.CustomerRepository;
import com.bilalkose.creditapplication.service.CreditScoreService;
import com.bilalkose.creditapplication.service.CustomerService;
import jakarta.persistence.EntityNotFoundException;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
@EqualsAndHashCode
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerDtoConverter customerDtoConverter;
    private final CreditScoreService creditScoreService;

    @Override
    public CustomerDto save(CreateCustomerRequest createCustomerRequest) {
        this.checkIfCustomerIsExistsByCitizenshipNumber(createCustomerRequest.getCitizenshipNumber());
        Customer customer = this.customerDtoConverter.convert(createCustomerRequest);
        customer.setCreditScore(this.creditScoreService.calculateCreditScoreForCustomerSave(customer));
        Customer savedCustomer = this.customerRepository.save(customer);
        log.info("Info SMS: User Saved. to:" + createCustomerRequest.getPhoneNumber() + " | " + createCustomerRequest.getCitizenshipNumber() + " / " +
                createCustomerRequest.getName() + " " + createCustomerRequest.getSurname());
        return this.customerDtoConverter.convert(savedCustomer);
    }

    @Override
    public CustomerDto update(CustomerDto customerDto) {
        this.checkIfCustomerIsExists(customerDto.getId());
        Customer customer = this.customerDtoConverter.convert(customerDto);
        Customer savedCustomer = this.customerRepository.save(customer);
        log.info("Info SMS: User Updated! : " + customerDto.getPhoneNumber() + ": " + customerDto.getCitizenshipNumber() + " / " +
                customerDto.getName() + " " + customerDto.getSurname());
        return this.customerDtoConverter.convert(savedCustomer);

    }

    @Override
    public CustomerDto getById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(
                        () -> new CustomerNotFoundException("Customer could not find by id: " + id)
                );
        CustomerDto customerDto = this.customerDtoConverter.convert(customer);
        return customerDto;
    }

    @Override
    public void delete(Long id) {
        this.checkIfCustomerIsExists(id);
        this.customerRepository.deleteById(id);
        log.info("Info SMS: User Deleted. to:" + getById(id).getPhoneNumber() + " | " + getById(id).getCitizenshipNumber() + " / " +
                getById(id).getName() + " " + getById(id).getSurname());
    }

    @Override
    public CustomerDto getCustomerByCitizenshipNumberAndBirthDate(GetCustomerCreditApplicationRequest
                                                                          getCustomerCreditApplicationRequest) {
        return this.customerDtoConverter.convert(this.customerRepository
                .findByCitizenshipNumberAndBirthday(
                        getCustomerCreditApplicationRequest.getCitizenshipNumber(),
                        getCustomerCreditApplicationRequest.getBirthDay()).orElseThrow(
                        () -> new CustomerNotFoundException("There is no user for this citizenship and birthday")
                ));
    }

    private void checkIfCustomerIsExists(Long id) {
        if (!this.customerRepository.existsById(id)) {
            throw new EntityNotFoundException("There is no customer for this id : " + id);
        }
    }

    private void checkIfCustomerIsExistsByCitizenshipNumber(String citizenshipNumber) {
        if (this.customerRepository.existsByCitizenshipNumber(citizenshipNumber)) {

            throw new AlreadyCreatedCustomerException("Already created for this citizenshipNumber : " + citizenshipNumber);
        }
    }
}
