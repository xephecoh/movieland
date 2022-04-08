package com.khamutov.movieland.web.services;


import com.khamutov.movieland.entity.CurrencyRate;

import java.util.List;

public interface CurrencyRateParserService {

    List<CurrencyRate> parse(String ratesAsString);
}
