package com.kaliszewski.datarelations.service.processing.impl;

import com.kaliszewski.datarelations.data.model.processing.DataProcessingTask;
import com.kaliszewski.datarelations.data.model.processing.ProgressStatus;
import com.kaliszewski.datarelations.data.model.reationship.Relationship;
import com.kaliszewski.datarelations.data.repository.DataProcessingTaskRepository;
import com.kaliszewski.datarelations.parser.RelationshipParser;
import com.kaliszewski.datarelations.service.file.FileService;
import com.kaliszewski.datarelations.service.processing.ProcessingService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Path;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

@Service
@Log4j2
@AllArgsConstructor
public class ProcessingServiceImpl implements ProcessingService {

    private DataProcessingTaskRepository dataProcessingTaskRepository;
    private ExecutorService executorService;
    private FileService fileService;
    private RelationshipParser relationshipParser;

    private void updateProcessingTaskStatus(DataProcessingTask task, ProgressStatus status) {
        task.setProgressStatus(status);
        log.info("Status was changed to {} for task with ID: {}", status, task.getId());
        dataProcessingTaskRepository.save(task);
    }

    private void updateProcessingTaskTmpFilePath(DataProcessingTask task, Path path) {
        task.setTmpFilePath(path.toString());
        dataProcessingTaskRepository.save(task);
    }


    @Override
    public Long initProcessing() {
        log.info("Processing start.");
        DataProcessingTask newTask = createNewTask();
        log.info("Created new task with ID: {} at {}", newTask.getId(), newTask.getStartTime());
        executorService.submit(() -> {
            scheduleTask(newTask);
        });
        return newTask.getId();
    }

    @Override
    public DataProcessingTask getTaskById(Long taskId) {
        return dataProcessingTaskRepository.getById(taskId);
    }


    private void scheduleTask(DataProcessingTask task) {
        task = handleFilePreparationAction(task);
        task = handleFileProcessingAction(task);
    }


    private DataProcessingTask handleFilePreparationAction(DataProcessingTask task) {
        log.info("Start first file preparation actions.");
        Path zipFilePath = fileService.downloadFile();
        if (zipFilePath != null) {
            updateProcessingTaskTmpFilePath(task, zipFilePath);
            updateProcessingTaskStatus(task, ProgressStatus.FILE_DOWNLOAD_SUCCESS);
            Path unzippedFilePath = fileService.unzipFile(zipFilePath);
            if(unzippedFilePath != null) {
                updateProcessingTaskStatus(task, ProgressStatus.FILE_PROCESSING_PENDING);
                updateProcessingTaskTmpFilePath(task, unzippedFilePath);
            } else {
                updateProcessingTaskStatus(task,ProgressStatus.FILE_PROCESSING_FAILED);
            }
        } else {
            updateProcessingTaskStatus(task, ProgressStatus.FILE_DOWNLOAD_FAILED);
        }
        updateProcessingTaskStatus(task, ProgressStatus.FILE_PROCESSING_SUCCESS);
        log.info("End first file preparation actions.");
        return task;
    }

    private DataProcessingTask handleFileProcessingAction(DataProcessingTask task) {
        log.info("Start file processing actions.");
        updateProcessingTaskStatus(task, ProgressStatus.DATA_PROCESSING_PENDING);
        long startTime = System.nanoTime();
        List<Relationship> relationships = relationshipParser.parse(Path.of(task.getTmpFilePath()).toFile());
        long estimatedTime = System.nanoTime() - startTime;
        double elapsedTimeInSecond = (double) estimatedTime / 1_000_000_000;
        log.info("Execution time: {} ns",estimatedTime);
        log.info("Execution time: {} s",elapsedTimeInSecond);
        log.info("Size {}", relationships.size());
        log.info("End file processing actions.");
        return task;
    }

    private DataProcessingTask createNewTask() {
        DataProcessingTask dataProcessingTask = buildNewTask();
        return dataProcessingTaskRepository.save(dataProcessingTask);
    }

    private DataProcessingTask buildNewTask() {
        DataProcessingTask dataProcessingTask = new DataProcessingTask();
        dataProcessingTask.setProgressStatus(ProgressStatus.FILE_DOWNLOAD_PENDING);
        dataProcessingTask.setStartTime(OffsetDateTime.now());
        return dataProcessingTask;
    }
}
