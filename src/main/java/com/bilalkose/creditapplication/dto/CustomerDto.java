package com.bilalkose.creditapplication.dto;

import com.bilalkose.creditapplication.model.CustomerDebt;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {
    private Long id;
    private String citizenshipNumber;
    private String name;
    private String surname;
    private int monthlyIncome; //Aylık Gelir
    private String phoneNumber;
    private LocalDate birthday; //localdatetime olacak
    private int creditScore;

    private List<CustomerDebt> customerDebts;
}
