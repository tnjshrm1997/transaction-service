package com.microservices.foundation.transactionservice.service.impl;

import com.microservices.foundation.transactionservice.dao.CustomerAccountTransactionDao;
import com.microservices.foundation.transactionservice.entity.CustomerAccountTransaction;
import com.microservices.foundation.transactionservice.model.CustomerTransactionDetailQueueResource;
import com.microservices.foundation.transactionservice.model.TransactionData;
import com.microservices.foundation.transactionservice.rabbitmq.CustomerTransactionExchange;
import com.microservices.foundation.transactionservice.service.CustomerTransactionService;
import com.microservices.foundation.transactionservice.utilities.ConvertToObjectUtility;
import com.microservices.foundation.transactionservice.proxies.CustomerServiceProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.Optional;

@Service
public class CustomerTransactionServiceImpl implements CustomerTransactionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerTransactionServiceImpl.class);
    final CustomerAccountTransactionDao customerAccountTransactionDao;
    final CustomerServiceProxy proxy;
    final CustomerTransactionExchange customerTransactionExchange;
    public CustomerTransactionServiceImpl(CustomerAccountTransactionDao customerAccountTransactionDao, CustomerServiceProxy proxy, CustomerTransactionExchange customerTransactionExchange) {
        this.customerAccountTransactionDao = customerAccountTransactionDao;
        this.proxy = proxy;
        this.customerTransactionExchange = customerTransactionExchange;
    }

    @Override
    public CustomerAccountTransaction createCustomerTransaction(String email, TransactionData transactionData) {
        if(transactionData.getTransactionType() ==null || email==null || transactionData.getAmount()==null)
            throw new InvalidParameterException();
        Optional<Long> accountNumber = getAccountNumber(email);
        if(accountNumber.isPresent()){
            CustomerAccountTransaction customerAccountTransaction = ConvertToObjectUtility.toCustomerTransactionObject(email,transactionData,accountNumber.get());
            CustomerTransactionDetailQueueResource queueResource = ConvertToObjectUtility.getCustomerServiceQueueResponse(customerAccountTransaction);
            LOGGER.info("Sending information to queue to update data for accountNumber: {}",accountNumber.get());
            customerTransactionExchange.publishMessageToQueue(queueResource);
            return customerAccountTransactionDao.save(customerAccountTransaction);
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
