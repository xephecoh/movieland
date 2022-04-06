package com.khamutov.movieland.entity;

import java.util.ArrayList;
import java.util.List;

public enum Currency {
    USD, EUR, IRR;
    private final static List<Currency> currencyList = new ArrayList<>();

    static {
        currencyList.add(Currency.USD);
        currencyList.add(Currency.EUR);
        currencyList.add(Currency.IRR);
    }
}
