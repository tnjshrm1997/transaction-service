package com.microservices.foundation.transactionservice.controller;


import com.microservices.foundation.transactionservice.model.TransactionData;
import com.microservices.foundation.transactionservice.service.CustomerTransactionService;
import com.microservices.foundation.transactionservice.entity.CustomerAccountTransaction;
import com.microservices.foundation.transactionservice.exception.TransactionServiceException;

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
    public ResponseEntity<String> createNewTransaction(@PathVariable String email, @RequestBody @Valid TransactionData transactionData){
        CustomerAccountTransaction customerTransaction= customerTransactionService.createCustomerTransaction(email,transactionData);
        if(customerTransaction!=null){
            String message = "Customer account is "+ customerTransaction.getTransactionType() +" with amount: "+customerTransaction.getAmount()+".";
            return ResponseEntity.of(Optional.of(message));
        }
        throw new TransactionServiceException("Account Number not found");
    }


}
