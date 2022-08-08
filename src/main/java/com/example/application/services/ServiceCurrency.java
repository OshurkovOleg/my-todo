package com.example.application.services;

import com.example.application.model.Currency;
import com.example.application.repository.CurrenciesRepository;

import java.util.List;
import java.util.stream.Collectors;

public class ServiceCurrency {
    public List<Currency> getListObjCurrencies() {
        return CurrenciesRepository.findCurrencies().stream()
                .map(Currency::new)
                .collect(Collectors.toList());
    }
}
