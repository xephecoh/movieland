package com.khamutov.movieland.services;

import com.khamutov.movieland.config.NationalBankConfig;
import com.khamutov.movieland.config.exception.CurrencyRateNotFoundException;
import com.khamutov.movieland.config.parser.CurrencyRateParser;
import com.khamutov.movieland.entity.CachedCurrencyRates;
import com.khamutov.movieland.entity.Currency;
import com.khamutov.movieland.entity.CurrencyRate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ehcache.Cache;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class CurrencyRateService {
    public static final String DATE_FORMAT = "dd/MM/yyyy";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);

    private final NationalBankRequester nationalBankRequester;
    private final CurrencyRateParser currencyRateParser;
    private final NationalBankConfig nationalBankConfig;
    private final Cache<LocalDate, CachedCurrencyRates> currencyRateCache;

    public CurrencyRate getCurrencyRate(Currency currency, LocalDate date) {
        log.info("getCurrencyRate. currency:{}, date:{}", currency, date);
        List<CurrencyRate> rates;
        CachedCurrencyRates cachedCurrencyRates = currencyRateCache.get(date);
        if (cachedCurrencyRates == null) {
            String ratesAsXml = nationalBankRequester.getRatesAsXml(nationalBankConfig.getUrl());
            rates = currencyRateParser.parse(ratesAsXml);
            currencyRateCache.put(date, new CachedCurrencyRates(rates));
        } else {
            rates = cachedCurrencyRates.getCurrencyRates();
        }

        return rates.stream().filter(rate -> currency.toString().equals(rate.getCc()))
                .findFirst()
                .orElseThrow(() -> new CurrencyRateNotFoundException("Currency Rate not found. Currency:" + currency + ", date:" + date));
    }
}
