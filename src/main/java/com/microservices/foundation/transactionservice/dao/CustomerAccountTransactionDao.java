package com.microservices.foundation.transactionservice.dao;


import com.microservices.foundation.transactionservice.entity.CustomerAccountTransaction;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CustomerAccountTransactionDao extends MongoRepository<CustomerAccountTransaction,String> {
    List<CustomerAccountTransaction> findByCreateDate(LocalDate date);
}
