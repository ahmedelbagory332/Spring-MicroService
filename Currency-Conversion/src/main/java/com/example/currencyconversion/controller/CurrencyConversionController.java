package com.example.currencyconversion.controller;


import com.example.currencyconversion.model.CurrencyConversion;
import com.example.currencyconversion.proxy.CurrencyExchangeProxy;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Objects;

@RestController
public class CurrencyConversionController {

    private final CurrencyExchangeProxy proxy;

    public CurrencyConversionController(CurrencyExchangeProxy proxy) {
        this.proxy = proxy;
    }
    private final Logger logger =
            LoggerFactory.getLogger(CurrencyConversionController.class);

    @GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
    // retry come from resilience4j to retry this api in case it fail
    // name property in @Retry the name in application.properties
    // fallbackMethod handle error in case api failed after retry
    @Retry(name = "currency-exchange-api", fallbackMethod = "errorResponse")
    public CurrencyConversion calculateCurrencyConversion(
            @PathVariable String from,
            @PathVariable String to,
            @PathVariable BigDecimal quantity
    ) {
        logger.info("currency-exchange api call received");

        HashMap<String, String> uriVariables = new HashMap<>();
        uriVariables.put("from",from);
        uriVariables.put("to",to);

        // wrong url to test resilience4j retry
//        ResponseEntity<CurrencyConversion> responseEntity = new RestTemplate().getForEntity
//                ("http://localhost:8000/currency-fexchange/from/{from}/to/{to}",
//                        CurrencyConversion.class, uriVariables);

        ResponseEntity<CurrencyConversion> responseEntity = new RestTemplate().getForEntity
                ("http://localhost:8000/currency-exchange/from/{from}/to/{to}",
                        CurrencyConversion.class, uriVariables);

        CurrencyConversion currencyConversion = responseEntity.getBody();

        return new CurrencyConversion(Objects.requireNonNull(currencyConversion).getId(),
                from, to, quantity,
                currencyConversion.getConversionMultiple(),
                quantity.multiply(currencyConversion.getConversionMultiple()),
                currencyConversion.getEnvironment()+ " " + "rest template");

    }
    // the name of this method should match the [fallbackMethod] params name where in @Retry
    // handle error in case api failed after retry
    // the method must has same return type of parent which is [calculateCurrencyConversion] in this case
    public CurrencyConversion errorResponse(Exception ex) {
        return  new CurrencyConversion();
    }


    /*

      using Feign is another simple way to connect to microservice with less code
      see code in calculateCurrencyConversion vs calculateCurrencyConversionFeign

    */
    @GetMapping("/currency-conversion-feign/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversion calculateCurrencyConversionFeign(
            @PathVariable String from,
            @PathVariable String to,
            @PathVariable BigDecimal quantity
    ) {

        CurrencyConversion currencyConversion = proxy.retrieveExchangeValue(from, to);

        return new CurrencyConversion(currencyConversion.getId(),
                from, to, quantity,
                currencyConversion.getConversionMultiple(),
                quantity.multiply(currencyConversion.getConversionMultiple()),
                currencyConversion.getEnvironment() + " " + "feign");

    }

}
