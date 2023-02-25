package com.bilalkose.creditapplication.dto.converter;

import com.bilalkose.creditapplication.dto.CreditApplicationDto;
import com.bilalkose.creditapplication.model.CreditApplication;
import org.springframework.stereotype.Component;

@Component
public class CreditApplicationDtoConverter {
    private final CustomerDtoConverter customerDtoConverter;

    public CreditApplicationDtoConverter(CustomerDtoConverter customerDtoConverter) {
        this.customerDtoConverter = customerDtoConverter;
    }

    public CreditApplicationDto convert (CreditApplication from){
        return new CreditApplicationDto(
                from.getId(),
                from.getCreationTime(),
                customerDtoConverter.convert(from.getCustomer())
        );

    }
}
