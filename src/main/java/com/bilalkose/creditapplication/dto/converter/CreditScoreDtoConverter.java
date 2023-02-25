package com.bilalkose.creditapplication.dto.converter;

import org.springframework.stereotype.Component;

@Component
public class CreditScoreDtoConverter {

    private final CustomerDtoConverter customerDtoConverter;

    public CreditScoreDtoConverter(CustomerDtoConverter customerDtoConverter) {
        this.customerDtoConverter = customerDtoConverter;
    }

}
