package com.microservices.foundation.transactionservice.service.impl;

import com.microservices.foundation.transactionservice.dao.CustomerAccountTransactionDao;
import com.microservices.foundation.transactionservice.entity.CustomerAccountTransaction;
import com.microservices.foundation.transactionservice.model.CustomerTransactionDetailQueueResource;
import com.microservices.foundation.transactionservice.model.TransactionData;
import com.microservices.foundation.transactionservice.service.CustomerTransactionService;
import com.microservices.foundation.transactionservice.stream.TransactionRequestProducer;
import com.microservices.foundation.transactionservice.utilities.ConvertToObjectUtility;
import com.microservices.foundation.transactionservice.proxies.CustomerServiceProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.*;
import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CustomerTransactionServiceImpl implements CustomerTransactionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerTransactionServiceImpl.class);
    private static final String FILEPATH = "/Users/ashim.sharma/IdeaProjects/microservices/transaction-service/src/main/resources/transactionHistory.txt";
    final CustomerAccountTransactionDao customerAccountTransactionDao;
    final CustomerServiceProxy proxy;
    final TransactionRequestProducer transactionRequestProducer;
    public CustomerTransactionServiceImpl(CustomerAccountTransactionDao customerAccountTransactionDao, CustomerServiceProxy proxy, TransactionRequestProducer transactionRequestProducer) {
        this.customerAccountTransactionDao = customerAccountTransactionDao;
        this.proxy = proxy;
        this.transactionRequestProducer = transactionRequestProducer;
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
            writeToFile(transactionData);
            transactionRequestProducer.sendRequest(queueResource);
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

    public void writeToFile(TransactionData transactionData){
        try(BufferedWriter writer = new BufferedWriter(
                new FileWriter(FILEPATH,true))){
            writer.write(transactionData.toString());
        }catch (IOException exception){
            LOGGER.error("Exception occurred while writing to file: {0}",exception);
        }
    }

    @Override
    public BigDecimal getTotalTransactions(String transType) {
        List<CustomerAccountTransaction> customerAccountTransactionList = customerAccountTransactionDao.findAll();
        Optional<BigDecimal> transactions = customerAccountTransactionList.stream()
                .filter(custTransaction -> custTransaction.getTransactionType().equalsIgnoreCase(transType))
                .map(CustomerAccountTransaction::getAmount)
                .reduce((trans1,trans2) -> trans1.add(trans2));
        if(transactions.isPresent()) return transactions.get();
        return null;
    }
    @Override
    public Map<String,BigDecimal> getTotalTransactionsByDate(LocalDate date){
        List<CustomerAccountTransaction> customerAccountTransactionList = customerAccountTransactionDao.findByCreateDate(date);
        Map<String, List<CustomerAccountTransaction>> transactionsByType = customerAccountTransactionList.stream()
                .collect(Collectors.groupingBy(CustomerAccountTransaction::getTransactionType));
        Map<String, BigDecimal> transactionDetails = new HashMap<>();
        transactionsByType.forEach((type, transactionObject)->{
            Optional<BigDecimal> amount = transactionObject.stream()
                     .map(CustomerAccountTransaction::getAmount)
                     .reduce((trans1,trans2) -> trans1.add(trans2));
            if(amount.isPresent()) transactionDetails.put(type,amount.get());
        });

        return transactionDetails;
    }


}
