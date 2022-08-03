package com.example.application.repository;

import java.util.ArrayList;
import java.util.List;

public class Currency {
    private final int id;
    private final String name;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Currency(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static List<Currency> getListObjCurrencies() {
        List<Currency> listCurrencies = new ArrayList<>();

        CurrenciesRepository.selectValueFromDataBaseTreeMap()
                .forEach((key, value) -> listCurrencies.add(new Currency(key, value))); //TODO получать список объектов из терминейт

        return listCurrencies;
    }
}
