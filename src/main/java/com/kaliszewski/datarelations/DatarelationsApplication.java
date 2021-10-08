package com.kaliszewski.datarelations;

import com.kaliszewski.datarelations.configuration.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication()
@EnableConfigurationProperties()
@EnableMongoRepositories()
public class DatarelationsApplication {
    public static void main(String[] args) {
        SpringApplication.run(DatarelationsApplication.class, args);
    }
}
