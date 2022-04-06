package com.khamutov.movieland.entity;

import lombok.Value;

import java.util.List;

@Value
public class CachedCurrencyRates {
    List<CurrencyRate> currencyRates;
}
