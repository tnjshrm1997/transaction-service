package com.microservices.foundation.transactionservice.controller;


import com.microservices.foundation.transactionservice.model.TransactionData;
import com.microservices.foundation.transactionservice.service.CustomerTransactionService;
import com.microservices.foundation.transactionservice.entity.CustomerAccountTransaction;
import com.microservices.foundation.transactionservice.exception.TransactionServiceException;

import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/transaction-service")
public class CustomerTransactionController {
    final CustomerTransactionService customerTransactionService;

    public CustomerTransactionController(CustomerTransactionService customerTransactionService) {
        this.customerTransactionService = customerTransactionService;
    }

    @PostMapping("/transaction/{email}")
    @Retry(name = "new-transaction", fallbackMethod = "createNewTransactionFallBack")
    public ResponseEntity<String> createNewTransaction(@PathVariable String email, @RequestBody @Valid TransactionData transactionData){
        CustomerAccountTransaction customerTransaction= customerTransactionService.createCustomerTransaction(email,transactionData);
        if(customerTransaction!=null){
            String message = "Customer account is "+ customerTransaction.getTransactionType() +" with amount: "+customerTransaction.getAmount()+".";
            return ResponseEntity.of(Optional.of(message));
        }
        throw new TransactionServiceException("Account Number not found");
    }
    public ResponseEntity<String> createNewTransactionFallBack(@PathVariable String email, @RequestBody @Valid TransactionData transactionData, Exception exception){
        throw new TransactionServiceException("Customer Service Not Available", exception);
    }
    @GetMapping("/transaction/getTotalTransactions/{transType}")
    public ResponseEntity<String> getTotalTransactions(@PathVariable String transType){
        String responseMessage = "Total "+ transType + " : "+ customerTransactionService.getTotalTransactions(transType);
         return ResponseEntity.of(Optional.of(responseMessage));
    }
    @GetMapping("/transaction/getTotalTransactionsByDate/{date}")
    public ResponseEntity<Map<String, BigDecimal>> getTotalTransactionsByDate(@PathVariable String date){
        Map<String, BigDecimal> data  =customerTransactionService.getTotalTransactionsByDate(LocalDate.parse(date));
        return ResponseEntity.of(Optional.of(data));
    }

}
