package com.bilalkose.creditapplication.model;

import com.bilalkose.creditapplication.enums.CreditApplicationResult;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CreditApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    private LocalDateTime creationTime;

    @Enumerated(EnumType.STRING)
    private CreditApplicationResult creditApplicationResult;

    private Double creditAmount;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name="customer_id", nullable = false)
    private Customer customer;

    @Override
    public String toString() {
        return "CreditApplication{" +
                "id='" + id + '\'' +
                ", creditApplicationDate=" + creationTime +
                '}';
    }
}
