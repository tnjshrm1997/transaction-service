package com.microservices.foundation.transactionservice.entity;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document
public class CustomerAccountTransaction {
    @Id
    private UUID transactionId;
    private String customerEmail;
    private String transactionType;
    private BigDecimal amount;
    private Long accountNumber;
}
