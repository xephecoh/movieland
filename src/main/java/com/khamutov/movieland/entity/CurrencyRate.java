package com.khamutov.movieland.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@AllArgsConstructor(onConstructor = @__(@JsonCreator))
@Builder
public class CurrencyRate {
    String txt;
    String rate;
    String cc;
    String exchangeDate;
}
