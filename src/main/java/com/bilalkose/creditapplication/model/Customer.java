package com.bilalkose.creditapplication.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;


@Entity
@NoArgsConstructor
@Data
@AllArgsConstructor
@Builder
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String citizenshipNumber;
    private String name;
    private String surname;
    private int monthlyIncome;
    @Column(unique = true)
    private String phoneNumber;
    private LocalDate birthday;
    private int creditScore;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.MERGE)
    private List<CreditApplication> creditApplications;

    @OneToMany(mappedBy = "customer")
    @JsonManagedReference
    private List<CustomerDebt> customerDebts;
}