package com.khamutov.movieland.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "nb")
public class NationalBankConfig {
    String url;
}
