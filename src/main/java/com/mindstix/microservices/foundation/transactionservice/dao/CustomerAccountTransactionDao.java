package com.mindstix.microservices.foundation.transactionservice.dao;


import com.mindstix.microservices.foundation.transactionservice.entity.CustomerAccountTransaction;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerAccountTransactionDao extends MongoRepository<CustomerAccountTransaction,String> {
}
