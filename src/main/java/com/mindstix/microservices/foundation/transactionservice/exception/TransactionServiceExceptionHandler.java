package com.mindstix.microservices.foundation.transactionservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class TransactionServiceExceptionHandler {
    private static final HttpStatus HTTP_BADREQUEST = HttpStatus.BAD_REQUEST;
    @ExceptionHandler(value = {TransactionServiceException.class})
    ResponseEntity<Object> handleTransactionException(TransactionServiceException ex){
        TransactionException transactionException=toExceptionMessageObject(ex.getMessage(), HTTP_BADREQUEST);
        return new ResponseEntity<>(transactionException,HTTP_BADREQUEST);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<Object> validationErrors(BindException exception){

        TransactionException transactionException= toExceptionMessageObject(exception.getAllErrors().toString(), HTTP_BADREQUEST);
        return new ResponseEntity<>(transactionException,HTTP_BADREQUEST);
    }
    public static TransactionException toExceptionMessageObject(String message, HttpStatus status){
        return TransactionException
                .builder()
                .message(message)
                .httpStatus(status)
                .build();
    }
}
