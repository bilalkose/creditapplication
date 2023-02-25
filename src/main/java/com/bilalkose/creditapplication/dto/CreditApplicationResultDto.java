package com.bilalkose.creditapplication.dto;

import com.bilalkose.creditapplication.enums.CreditApplicationResult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreditApplicationResultDto {
    private CreditApplicationResult creditApplicationResult;

    private Double creditAmount;
}
