package com.bilalkose.creditapplication.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreditApplicationDto {
    private long id;
    private LocalDateTime creditApplicationDate;
    private CustomerDto customerDto;

    public CreditApplicationDto(long id,LocalDateTime creditApplicationDate, CustomerDto customerDto) {
        this.id = id;
        this.creditApplicationDate = creditApplicationDate;
        this.customerDto = customerDto;
    }
}
