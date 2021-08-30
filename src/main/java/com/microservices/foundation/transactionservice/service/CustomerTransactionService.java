package com.microservices.foundation.transactionservice.service;

import com.microservices.foundation.transactionservice.model.TransactionData;
import com.microservices.foundation.transactionservice.entity.CustomerAccountTransaction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;

public interface CustomerTransactionService {
    CustomerAccountTransaction createCustomerTransaction(String email, TransactionData transactionData);
    Optional<Long> getAccountNumber(String email);
    BigDecimal getTotalTransactions(String transType);
    Map<String,BigDecimal> getTotalTransactionsByDate(LocalDate date);
}

