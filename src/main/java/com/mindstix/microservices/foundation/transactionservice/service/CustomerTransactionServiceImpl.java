package com.mindstix.microservices.foundation.transactionservice.service;

import com.mindstix.microservices.foundation.transactionservice.entity.CustomerAccountTransaction;
import com.mindstix.microservices.foundation.transactionservice.model.CustomerTransactionDetailQueueResource;
import com.mindstix.microservices.foundation.transactionservice.model.TransactionData;
import com.mindstix.microservices.foundation.transactionservice.proxies.CustomerServiceProxy;
import com.mindstix.microservices.foundation.transactionservice.repository.CustomerAccountTransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.Optional;

@Service
public class CustomerTransactionServiceImpl implements CustomerTransactionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerTransactionServiceImpl.class);
    final CustomerAccountTransactionRepository customerAccountTransactionRepository;
    final CustomerServiceProxy proxy;
    public CustomerTransactionServiceImpl(CustomerAccountTransactionRepository customerAccountTransactionRepository, CustomerServiceProxy proxy) {
        this.customerAccountTransactionRepository = customerAccountTransactionRepository;
        this.proxy = proxy;
    }

    @Override
    public CustomerAccountTransaction createCustomerTransaction(String email, TransactionData transactionData) {
        if(transactionData.getTransactionType() ==null || email==null || transactionData.getAmount()==null)
            throw new InvalidParameterException();
        Optional<Long> accountNumber = getAccountNumber(email);
        if(accountNumber.isPresent()){
            CustomerAccountTransaction customerAccountTransaction = CustomerTransactionService.toCustomerTransactionObject(email,transactionData,accountNumber.get());
            CustomerTransactionDetailQueueResource queueResource = CustomerTransactionService.getCustomerServiceQueueResponse(customerAccountTransaction);
            LOGGER.info("Sending information to queue to update data for accountNumber: {}",accountNumber.get());
            return customerAccountTransactionRepository.save(customerAccountTransaction);
        }
        LOGGER.info("Account doesn't Exists in system");
        return null;
    }

    @Override
    public Optional<Long> getAccountNumber(String email) {
        LOGGER.info("Trying to get account number from customer-service..");
        return Optional.ofNullable(proxy.getCustomerAccountNumber(email));
    }

}
