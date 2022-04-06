package com.khamutov.movieland.config;

import com.khamutov.movieland.entity.Currency;
import org.springframework.core.convert.converter.Converter;

public class StringToEnumConverter implements Converter<String, Currency> {
    @Override
    public Currency convert(String source) {
        return Currency.valueOf(source.toUpperCase());
    }
}
