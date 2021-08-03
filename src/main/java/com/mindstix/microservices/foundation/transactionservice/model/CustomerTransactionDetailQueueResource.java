package com.mindstix.microservices.foundation.transactionservice.model;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
@Builder
public class CustomerTransactionDetailQueueResource implements Serializable{
    private Long accountNumber;
    private String transactionType;
    private double amount;
}
