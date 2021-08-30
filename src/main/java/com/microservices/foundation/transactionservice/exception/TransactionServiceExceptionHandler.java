package com.microservices.foundation.transactionservice.exception;

import com.microservices.foundation.transactionservice.utilities.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class TransactionServiceExceptionHandler {
    @ExceptionHandler(value = {TransactionServiceException.class})
    ResponseEntity<Object> handleTransactionException(TransactionServiceException ex){
        TransactionException transactionException=toExceptionMessageObject(ex.getMessage(), Constants.HTTP_BADREQUEST);
        return new ResponseEntity<>(transactionException,Constants.HTTP_BADREQUEST);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<Object> validationErrors(BindException exception){
        TransactionException transactionException= toExceptionMessageObject(exception.getAllErrors().toString(), Constants.HTTP_BADREQUEST);
        return new ResponseEntity<>(transactionException,Constants.HTTP_BADREQUEST);
    }
    public static TransactionException toExceptionMessageObject(String message, HttpStatus status){
        return TransactionException
                .builder()
                .message(message)
                .httpStatus(status)
                .build();
    }
}
