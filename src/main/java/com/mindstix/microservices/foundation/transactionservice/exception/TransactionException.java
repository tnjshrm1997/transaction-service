package com.mindstix.microservices.foundation.transactionservice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
public class TransactionException {
    private String message;
    private HttpStatus httpStatus;
}
