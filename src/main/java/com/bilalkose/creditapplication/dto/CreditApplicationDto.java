package com.bilalkose.creditapplication.dto;

import com.bilalkose.creditapplication.enums.CreditApplicationResult;
import com.bilalkose.creditapplication.model.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreditApplicationDto {
    private Long id;

    private LocalDateTime creationTime;

    private CreditApplicationResult creditApplicationResult;

    private Double creditAmount;

    private long customerId;
}
