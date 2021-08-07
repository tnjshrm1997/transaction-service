package com.microservices.foundation.transactionservice.rabbitmq;

import com.microservices.foundation.transactionservice.model.CustomerTransactionDetailQueueResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class CustomerTransactionExchange {
    private final RabbitTemplate rabbitTemplate;
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerTransactionExchange.class);

    private static final String EXCHANGE = "transaction_exchange";
    private static final String ROUTING_KEY = "transaction.queue";

    public CustomerTransactionExchange(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }
    public void publishMessageToQueue(CustomerTransactionDetailQueueResource customerTransaction){
        LOGGER.info("Publishing the Customer Service Queue");
        rabbitTemplate.convertAndSend(EXCHANGE, ROUTING_KEY, customerTransaction);
    }
}
