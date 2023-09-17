package com.example.currencyexchange.service;


import com.example.currencyexchange.model.CurrencyExchange;
import org.springframework.stereotype.Service;
import com.example.currencyexchange.repo.CurrencyExchangeRepository;

@Service
public class CurrencyExchangeService {

    private final CurrencyExchangeRepository currencyExchangeRepository;

    public CurrencyExchangeService(CurrencyExchangeRepository currencyExchangeRepository) {
        this.currencyExchangeRepository = currencyExchangeRepository;
    }

    public CurrencyExchange findByFromAndTo(String from , String to) {
        return currencyExchangeRepository.findByFromAndTo(from, to).orElse(null);
    }

}
