package com.mindstix.microservices.foundation.transactionservice.repository;


import com.mindstix.microservices.foundation.transactionservice.entity.CustomerAccountTransaction;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerAccountTransactionRepository extends MongoRepository<CustomerAccountTransaction,String> {
}
