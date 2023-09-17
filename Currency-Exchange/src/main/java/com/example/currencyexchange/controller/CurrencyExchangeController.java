package com.example.currencyexchange.controller;


 import com.example.currencyexchange.model.CurrencyExchange;
 import com.example.currencyexchange.service.CurrencyExchangeService;
 import org.slf4j.Logger;
 import org.slf4j.LoggerFactory;
 import org.springframework.core.env.Environment;
 import org.springframework.web.bind.annotation.GetMapping;
 import org.springframework.web.bind.annotation.PathVariable;
 import org.springframework.web.bind.annotation.RestController;

 import java.math.BigDecimal;

@RestController
public class CurrencyExchangeController {

    private final Environment environment;
    private final CurrencyExchangeService currencyExchangeService;

    public CurrencyExchangeController(Environment environment, CurrencyExchangeService currencyExchangeService) {
        this.environment = environment;
        this.currencyExchangeService = currencyExchangeService;
    }

    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    public CurrencyExchange currencyExchange(@PathVariable String from , @PathVariable String to) {
        CurrencyExchange currencyExchange
                = currencyExchangeService.findByFromAndTo(from, to);
        if(currencyExchange ==null) {
            throw new RuntimeException
                    ("Unable to Find data for " + from + " to " + to);
        }

        String port =  environment.getProperty("local.server.port");
        currencyExchange.setEnvironment(port);
     return currencyExchange;
    }




}
