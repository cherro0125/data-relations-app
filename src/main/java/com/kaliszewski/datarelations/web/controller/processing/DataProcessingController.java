package com.kaliszewski.datarelations.web.controller.processing;

import com.kaliszewski.datarelations.data.model.processing.DataProcessingTask;
import com.kaliszewski.datarelations.data.response.DataProcessingTaskInitResponse;
import com.kaliszewski.datarelations.data.response.DataProcessingTaskResponse;
import com.kaliszewski.datarelations.service.processing.ProcessingService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/processing")
@Log4j2
@AllArgsConstructor
public class DataProcessingController {

    private ProcessingService processingService;

    @Tag(name = "Processing: init", description = "Init data processing with new file download.")
    @PostMapping(path = "/init")
    public ResponseEntity<DataProcessingTaskInitResponse> init() {
        Long taskId = processingService.initProcessing();
        DataProcessingTask task = processingService.getTaskById(taskId);
        return ResponseEntity.of(Optional.of(new DataProcessingTaskInitResponse(taskId, task.getProgressStatus(),
                "Please use /api/processing/status/{taskId} for check details")));
    }


    @Tag(name = "Processing: status", description = "Get status for task with provided ID")
    @GetMapping("/status/{taskId}")
    public ResponseEntity<DataProcessingTaskResponse> getTask(@PathVariable("taskId") Long taskId) {
        DataProcessingTask task = processingService.getTaskById(taskId);
        return ResponseEntity.of(Optional.of(new DataProcessingTaskResponse(task.getId(),task.getProgressStatus())));
    }

    @GetMapping("/test")
    public String test() {
        processingService.test();
        return "OK";
    }


}
