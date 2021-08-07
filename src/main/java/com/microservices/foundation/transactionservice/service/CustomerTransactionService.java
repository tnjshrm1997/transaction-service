package com.microservices.foundation.transactionservice.service;

import com.microservices.foundation.transactionservice.model.TransactionData;
import com.microservices.foundation.transactionservice.entity.CustomerAccountTransaction;

import java.util.Optional;

public interface CustomerTransactionService {
    CustomerAccountTransaction createCustomerTransaction(String email, TransactionData transactionData);
    Optional<Long> getAccountNumber(String email);
}
