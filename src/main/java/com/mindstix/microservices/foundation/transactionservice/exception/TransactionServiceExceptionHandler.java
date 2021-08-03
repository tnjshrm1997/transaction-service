package com.mindstix.microservices.foundation.transactionservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class TransactionServiceExceptionHandler {
    private static final HttpStatus HTTP_BADREQUEST = HttpStatus.BAD_REQUEST;
    @ExceptionHandler(value = {TransactionServiceException.class})
    ResponseEntity<Object> handleTransactionException(TransactionServiceException e){
        TransactionException transactionException= new TransactionException(e.getMessage(),
                HTTP_BADREQUEST);
        return new ResponseEntity<>(transactionException,HTTP_BADREQUEST);
    }
}
