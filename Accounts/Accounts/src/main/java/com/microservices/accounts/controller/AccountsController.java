package com.microservices.accounts.controller;

import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservices.accounts.config.AccountsServiceConfig;
import com.microservices.accounts.model.Accounts;
import com.microservices.accounts.model.Customer;
import com.microservices.accounts.model.Properties;
import com.microservices.accounts.repository.AccountsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class AccountsController {
    @Autowired
    private AccountsRepository accountsRepository;

    @Autowired
    private AccountsServiceConfig accountsServiceConfig;

    @PostMapping("/myAccount")
    public Accounts getAccountDetails(@RequestBody Customer customer) {

        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId());
        if (accounts != null) {
            return accounts;
        } else {
            return null;
        }

    }

    @GetMapping("/account/properties")
    public  String getPropertyDetails() throws JsonProcessingException {
        ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
        Properties properties = new Properties(
            accountsServiceConfig.getMsg(),
            accountsServiceConfig.getBuildVersion(),
            accountsServiceConfig.getMailDetails(),
            accountsServiceConfig.getActiveBranches());
        return objectWriter.writeValueAsString(properties);
    }
}
