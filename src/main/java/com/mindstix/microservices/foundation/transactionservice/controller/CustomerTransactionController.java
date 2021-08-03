package com.mindstix.microservices.foundation.transactionservice.controller;


import com.mindstix.microservices.foundation.transactionservice.entity.CustomerAccountTransaction;
import com.mindstix.microservices.foundation.transactionservice.exception.TransactionServiceException;
import com.mindstix.microservices.foundation.transactionservice.model.TransactionData;
import com.mindstix.microservices.foundation.transactionservice.service.CustomerTransactionService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/transaction-service")
public class CustomerTransactionController {
    final CustomerTransactionService customerTransactionService;

    public CustomerTransactionController(CustomerTransactionService customerTransactionService) {
        this.customerTransactionService = customerTransactionService;
    }

    @PostMapping("/transaction/{email}")
    public ResponseEntity<CustomerAccountTransaction> createNewTransaction(@PathVariable String email, @RequestBody @Valid TransactionData transactionData){
        CustomerAccountTransaction customerTransaction= customerTransactionService.createCustomerTransaction(email,transactionData);
        if(customerTransaction!=null){
            return ResponseEntity.of(Optional.of(customerTransaction));
        }
        throw new TransactionServiceException("Account Number not found");
    }


}