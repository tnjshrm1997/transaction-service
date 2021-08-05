package com.mindstix.microservices.foundation.transactionservice.service;

import com.mindstix.microservices.foundation.transactionservice.entity.CustomerAccountTransaction;
import com.mindstix.microservices.foundation.transactionservice.model.TransactionData;

import java.util.Optional;

public interface CustomerTransactionService {
    CustomerAccountTransaction createCustomerTransaction(String email, TransactionData transactionData);
    Optional<Long> getAccountNumber(String email);
}
