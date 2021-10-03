package com.kaliszewski.datarelations.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Configuration
@ConfigurationProperties(prefix = "app")
@Data
public class AppProperties {
    private String zipFileUrl;
    private String tmpDir;
    private Long timeoutInMinutes;
}
