package com.khamutov.movieland.config.parser;


import com.khamutov.movieland.model.CurrencyRate;

import java.util.List;

public interface CurrencyRateParser {

    List<CurrencyRate> parse(String ratesAsString);
}
