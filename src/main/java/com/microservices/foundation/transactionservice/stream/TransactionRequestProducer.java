package com.microservices.foundation.transactionservice.stream;


import com.microservices.foundation.transactionservice.model.CustomerTransactionDetailQueueResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.integration.support.MessageBuilder;

@EnableBinding({Source.class})
public class TransactionRequestProducer {
    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionRequestProducer.class);
    private final Source source;
    public TransactionRequestProducer(Source source) {
        this.source = source;
    }
    public void sendRequest(CustomerTransactionDetailQueueResource transactionDetails){
        LOGGER.info("Sending Output : {}",transactionDetails);
        source.output().send(MessageBuilder.withPayload(transactionDetails).build());
    }
}
