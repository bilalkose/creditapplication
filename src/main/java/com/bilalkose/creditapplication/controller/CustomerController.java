package com.bilalkose.creditapplication.controller;

import com.bilalkose.creditapplication.dto.CustomerDto;
import com.bilalkose.creditapplication.dto.request.CreateCustomerRequest;
import com.bilalkose.creditapplication.model.Customer;
import com.bilalkose.creditapplication.service.CustomerService;
import com.bilalkose.creditapplication.service.impl.CustomerServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/customer")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerDto> getCustomerById(@PathVariable Long customerId) {
        return ResponseEntity.ok(this.customerService.getById(customerId));
    }

    @PostMapping
    public void createCustomer(@RequestBody @Valid CreateCustomerRequest createCustomerRequest) {
        this.customerService.save(createCustomerRequest);
    }
}
