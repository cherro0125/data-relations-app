package com.kaliszewski.datarelations.web.controller.processing;

import com.kaliszewski.datarelations.data.model.processing.DataProcessingTask;
import com.kaliszewski.datarelations.data.model.processing.ProgressStatus;
import com.kaliszewski.datarelations.service.file.FileService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Path;
import java.time.OffsetDateTime;

@RestController
@RequestMapping("/processing")
@Log4j2
@AllArgsConstructor
public class DataProcessingController {

    private FileService fileService;

    @Tag(name = "Processing: init", description = "Init data processing with new file download.")
    @PostMapping(path = "/init")
    public ResponseEntity<DataProcessingTask> init() {
        Path path = fileService.downloadFile();
        log.info(path);
        Path unzipPath = fileService.unzipFile(path);
        log.info(unzipPath);
        return new ResponseEntity<>(new DataProcessingTask(1L, ProgressStatus.DATA_PROCESSING_PENDING, OffsetDateTime.now(),OffsetDateTime.now().plusMinutes(50L),""), HttpStatus.OK);
    }
}
