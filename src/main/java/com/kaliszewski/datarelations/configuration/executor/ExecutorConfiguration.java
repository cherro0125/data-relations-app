package com.kaliszewski.datarelations.configuration.executor;

import com.kaliszewski.datarelations.configuration.AppProperties;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
@AllArgsConstructor
public class ExecutorConfiguration {

    private AppProperties appProperties;

    @Bean
    @Transactional
    public ExecutorService executorService() {
        return Executors.newFixedThreadPool(appProperties.getExecutorThreads());
    }
}
