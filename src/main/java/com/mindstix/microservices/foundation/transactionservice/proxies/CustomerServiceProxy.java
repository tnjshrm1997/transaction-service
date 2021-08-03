package com.mindstix.microservices.foundation.transactionservice.proxies;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="customer-service")
public interface CustomerServiceProxy {
    @GetMapping("/customer-service/account/{emailId}")
    Long getCustomerAccountNumber (@PathVariable String emailId);
}
