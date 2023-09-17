package com.example.currencyconversion.proxy;

import com.example.currencyconversion.model.CurrencyConversion;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


// name is the name of microservice we want to connect to and will found in application.properties of the currency exchange service
@FeignClient(name="currency-exchange") // we use eureka to get url
//@FeignClient(name="currency-exchange", url="localhost:8000")
public interface CurrencyExchangeProxy {
    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    public CurrencyConversion retrieveExchangeValue(
            @PathVariable String from,
            @PathVariable String to);
}
