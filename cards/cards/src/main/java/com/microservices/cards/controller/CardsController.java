package com.microservices.cards.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.microservices.cards.config.CardsServiceConfig;
import com.microservices.cards.model.Cards;
import com.microservices.cards.model.Customer;
import com.microservices.cards.model.Properties;
import com.microservices.cards.repository.CardsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CardsController {

    @Autowired
    private CardsRepository cardsRepository;

    @Autowired
    CardsServiceConfig cardsServiceConfig;

    @PostMapping("/myCards")
    public List<Cards> getCardDetails(@RequestBody Customer customer) {
        List<Cards> cards = cardsRepository.findByCustomerId(customer.getCustomerId());
        if (cards != null) {
            return cards;
        } else {
            return null;
        }

    }

    @GetMapping("/card/properties")
    public  String getPropertyDetails() throws JsonProcessingException {
        ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
        Properties properties = new Properties(
                cardsServiceConfig.getMsg(),
                cardsServiceConfig.getBuildVersion(),
                cardsServiceConfig.getMailDetails(),
                cardsServiceConfig.getActiveBranches());
        return objectWriter.writeValueAsString(properties);
    }

}
