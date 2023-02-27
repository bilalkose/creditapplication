package com.bilalkose.creditapplication.dto.request;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class CreateCustomerRequest {

    @NotNull
    @NotBlank
    @Size(min = 11,max = 11)
    private String citizenshipNumber;
    @NotNull
    @NotBlank
    private String name;
    @NotNull
    @NotBlank
    private String surname;
    @NotNull
    @Min(0)
    private int monthlyIncome;
    @NotNull
    @NotBlank
    @Size(min = 11,max = 11)
    private String phoneNumber;
    @NotNull
    private LocalDate birthday;
}
