# Transaction Service (Microservices Foundation)

## Introduction 

This service is developed to process transactions, this service interacts with a MongoDB database. It has two primary functions:
1. Store the transaction information into database. 
2. Send the transaction data to `customer-service` using RabbitMQ. 

## Implementation and Functionality

1. `POST /transaction-service/transaction/{email}` : Any transaction created by the customer will be consumed by this request. It will try to get customer information based on transaction data by calling `customer-service`. If the customer is present, the transaction data will be transmitted to the RabbitMQ exchange for processing by `customer-service`. In this call, we also tried implementing `try with resources` by saving each and every transaction data object to a file.
2. `GET /transaction-service/transaction/getTotalTransactions/{transType}`: This request was developed to try implementation of a mix of stream, filter, map, and reduce. This service retrieves all transactions of the specified type in a single call and returns the sum of all transactions.
3. `GET /transaction-service/transaction/getTotalTransactionsByDate/{date}` : This request was created to try implementing different stream elements such as Collectors. groupingBy, map, and reduce. This REST call will fetch data for the specified date and report the total of all transactions per transaction type.


