package com.kaliszewski.datarelations.service.file.impl;


import com.kaliszewski.datarelations.configuration.AppProperties;
import com.kaliszewski.datarelations.service.file.FileService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Service
@Log4j2
@AllArgsConstructor
public class FileServiceImpl implements FileService {

    private AppProperties appProperties;
    private HttpClient httpClient;


    @Override
    public Path downloadFile() {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(appProperties.getZipFileUrl()))
                .timeout(Duration.of(appProperties.getTimeoutInMinutes(), ChronoUnit.MINUTES))
                .version(HttpClient.Version.HTTP_2)
                .GET()
                .build();
        httpClient.followRedirects();
        CompletableFuture<HttpResponse<Path>> response = null;
        response = httpClient.sendAsync(httpRequest, HttpResponse.BodyHandlers.ofFileDownload(Path.of(appProperties.getTmpDir()), StandardOpenOption.CREATE,StandardOpenOption.WRITE));
        Path path = null;
        try {
            path = response.thenApply(HttpResponse::body).get(appProperties.getTimeoutInMinutes(), TimeUnit.MINUTES);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            log.error("Something get wrong while file download.",e);
        }
        return path;
    }

    @Override
    public Path unzipFile() {
        return null;
    }
}
