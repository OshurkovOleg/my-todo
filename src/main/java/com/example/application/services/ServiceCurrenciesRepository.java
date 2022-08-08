package com.example.application.services;

import com.example.application.repository.CurrenciesRepository;

import java.util.List;

public class ServiceCurrenciesRepository {

    //TODO если мы будем получать из базы в CurrenciesRepository мапу со всеми валютами (id, name)
    // то здесь мы можем добавить методы которые будут из мапы делать разные типы коллекций
    // первый метод будет возвращать list, второй метод будем возвращать Array (если это требуется)
    // TODO изменил логику в методе репозиторя, теперь полуаем только названия валют в виде листа
    public List<String> getListCurrencies() {
        return CurrenciesRepository.findCurrencies();
    }

    public void addCurrency(String name) {
        CurrenciesRepository.save(name);
    }

    public void deleteCurrency(String name) {
        CurrenciesRepository.delete(name);
    }


}
