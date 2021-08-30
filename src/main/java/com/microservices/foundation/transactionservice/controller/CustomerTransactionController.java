package com.microservices.foundation.transactionservice.controller;


import com.microservices.foundation.transactionservice.model.TransactionData;
import com.microservices.foundation.transactionservice.service.CustomerTransactionService;
import com.microservices.foundation.transactionservice.entity.CustomerAccountTransaction;
import com.microservices.foundation.transactionservice.exception.TransactionServiceException;

import com.mongodb.MongoException;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/transaction-service/transaction/")
public class CustomerTransactionController {
    final CustomerTransactionService customerTransactionService;

    public CustomerTransactionController(CustomerTransactionService customerTransactionService) {
        this.customerTransactionService = customerTransactionService;
    }

    @PostMapping("{email}")
    @Retry(name = "new-transaction", fallbackMethod = "createNewTransactionFallBack")
    public ResponseEntity<String> createNewTransaction(@PathVariable String email, @RequestBody @Valid TransactionData transactionData){
        CustomerAccountTransaction customerTransaction= customerTransactionService.createCustomerTransaction(email,transactionData);
        if(customerTransaction!=null){
            String message = "Customer account is "+ customerTransaction.getTransactionType() +" with amount: "+customerTransaction.getAmount()+".";
            return ResponseEntity.of(Optional.of(message));
        }
        throw new TransactionServiceException("Account Number not found");
    }

    @GetMapping("getTotalTransactions/{transType}")
    @Retryable(value = {MongoException.class}, maxAttempts = 2, backoff = @Backoff(delay = 1000))
    public ResponseEntity<String> getTotalTransactions(@PathVariable String transType){
        String responseMessage = "Total "+ transType + " : "+ customerTransactionService.getTotalTransactions(transType);
         return ResponseEntity.of(Optional.of(responseMessage));
    }
    @GetMapping("getTotalTransactionsByDate/{date}")
    @Retryable(value = {MongoException.class}, maxAttempts = 2, backoff = @Backoff(delay = 1000))
    public ResponseEntity<Map<String, BigDecimal>> getTotalTransactionsByDate(@PathVariable String date){
        Map<String, BigDecimal> data  =customerTransactionService.getTotalTransactionsByDate(LocalDate.parse(date));
        return ResponseEntity.of(Optional.of(data));
    }

    public ResponseEntity<String> createNewTransactionFallBack(@PathVariable String email, @RequestBody @Valid TransactionData transactionData, Exception exception){
        throw new TransactionServiceException("Customer Service Not Available", exception);
    }

}
