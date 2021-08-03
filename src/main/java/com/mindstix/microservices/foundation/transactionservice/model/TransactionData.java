package com.mindstix.microservices.foundation.transactionservice.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class TransactionData {
    String transactionType;
    Double amount;
}
