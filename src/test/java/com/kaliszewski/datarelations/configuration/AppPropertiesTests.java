package com.kaliszewski.datarelations.configuration;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AppPropertiesTests {

    @Autowired
    private AppProperties appProperties;

    @Test
    @DisplayName("Check is all property are correctly imported from test application.yml")
    public void checkPropertyValueTest() {
        assertEquals(appProperties.getTmpDir(), System.getProperty("java.io.tmpdir"));
        assertEquals(appProperties.getTimeoutInMinutes(), 1);
        assertEquals(appProperties.getExecutorThreads(), 2);
        assertEquals(appProperties.getZipFileUrl(), "https://leidata.gleif.org/api/v1/concatenated-files/rr/20210930/zip");
    }

}
