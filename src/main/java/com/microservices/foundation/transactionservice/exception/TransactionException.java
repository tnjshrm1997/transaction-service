package com.microservices.foundation.transactionservice.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class TransactionException {
    private String message;
    private HttpStatus httpStatus;
}
