package com.mindstix.microservices.foundation.transactionservice.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
public class TransactionData {
    @NotBlank
    @NotNull
    String transactionType;
    @NotNull
    Double amount;
}
