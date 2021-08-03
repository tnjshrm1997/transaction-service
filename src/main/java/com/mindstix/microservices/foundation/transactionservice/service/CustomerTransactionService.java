package com.mindstix.microservices.foundation.transactionservice.service;

import com.mindstix.microservices.foundation.transactionservice.entity.CustomerAccountTransaction;
import com.mindstix.microservices.foundation.transactionservice.model.CustomerTransactionDetailQueueResource;
import com.mindstix.microservices.foundation.transactionservice.model.TransactionData;

import java.util.Optional;
import java.util.UUID;

public interface CustomerTransactionService {
    CustomerAccountTransaction createCustomerTransaction(String email, TransactionData transactionData);
    Optional<Long> getAccountNumber(String email);

    static CustomerTransactionDetailQueueResource getCustomerServiceQueueResponse(CustomerAccountTransaction customerAccountTransaction) {
        return CustomerTransactionDetailQueueResource
                .builder()
                .transactionType(customerAccountTransaction.getTransactionType())
                .accountNumber(customerAccountTransaction.getAccountNumber())
                .amount(customerAccountTransaction.getAmount())
                .build();
    }

    static CustomerAccountTransaction toCustomerTransactionObject(String email, TransactionData transactionData, Long accountNumber) {
        return CustomerAccountTransaction
                .builder()
                .transactionId(UUID.randomUUID())
                .transactionType(transactionData.getTransactionType())
                .customerEmail(email)
                .accountNumber(accountNumber)
                .amount(transactionData.getAmount())
                .build();
    }

}
