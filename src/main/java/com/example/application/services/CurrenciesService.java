package com.example.application.services;

import com.example.application.model.Currency;
import com.example.application.repository.CurrenciesRepository;

import java.util.List;
import java.util.stream.Collectors;

public class CurrenciesService {

    private final CurrenciesRepository currenciesRepository = new CurrenciesRepository();

    public List<String> getListCurrencies() {
        return currenciesRepository.findCurrencies();
    }

    public void addCurrency(String name) {
        currenciesRepository.save(name);
    }

    public void deleteCurrency(String name) {
        currenciesRepository.delete(name);
    }

    public List<Currency> getListObjCurrencies() {
        return currenciesRepository.findCurrencies().stream()
                .map(Currency::new)
                .collect(Collectors.toList());
    }


}
