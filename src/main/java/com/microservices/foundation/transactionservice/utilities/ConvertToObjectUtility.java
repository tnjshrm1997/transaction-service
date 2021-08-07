package com.microservices.foundation.transactionservice.utilities;

import com.microservices.foundation.transactionservice.entity.CustomerAccountTransaction;
import com.microservices.foundation.transactionservice.model.CustomerTransactionDetailQueueResource;
import com.microservices.foundation.transactionservice.model.TransactionData;

import java.util.UUID;

public class ConvertToObjectUtility {

    public static CustomerTransactionDetailQueueResource getCustomerServiceQueueResponse(CustomerAccountTransaction customerAccountTransaction) {
        return CustomerTransactionDetailQueueResource
                .builder()
                .transactionType(customerAccountTransaction.getTransactionType())
                .accountNumber(customerAccountTransaction.getAccountNumber())
                .amount(customerAccountTransaction.getAmount())
                .build();
    }
    public static CustomerAccountTransaction toCustomerTransactionObject(String email, TransactionData transactionData, Long accountNumber) {
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
