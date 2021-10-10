package com.kaliszewski.datarelations.service.processing.impl;

import com.kaliszewski.datarelations.data.model.processing.DataProcessingTask;
import com.kaliszewski.datarelations.data.model.processing.ProgressStatus;
import com.kaliszewski.datarelations.data.model.reationship.NodeCorrelation;
import com.kaliszewski.datarelations.data.model.reationship.Relationship;
import com.kaliszewski.datarelations.data.parser.ParseResult;
import com.kaliszewski.datarelations.data.repository.DataProcessingTaskRepository;
import com.kaliszewski.datarelations.data.repository.NodeCorrelationRepository;
import com.kaliszewski.datarelations.data.repository.RelationshipRepository;
import com.kaliszewski.datarelations.parser.RelationshipParser;
import com.kaliszewski.datarelations.service.file.FileService;
import com.kaliszewski.datarelations.service.processing.ProcessingService;
import com.kaliszewski.datarelations.service.relationship.RelationshipService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.nio.file.Path;
import java.time.OffsetDateTime;
import java.util.*;
import java.util.concurrent.ExecutorService;

@Service
@Log4j2
@AllArgsConstructor
public class ProcessingServiceImpl implements ProcessingService {

    private DataProcessingTaskRepository dataProcessingTaskRepository;
    private ExecutorService executorService;
    private FileService fileService;
    private RelationshipParser relationshipParser;
    private RelationshipRepository relationshipRepository;
    private RelationshipService relationshipService;
    private NodeCorrelationRepository nodeCorrelationRepository;

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
        return dataProcessingTaskRepository.findById(taskId).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public void test() {
//        Set<String> distinctNodeIdsByTaskId = relationshipService.getDistinctNodeIdsByTaskId(6L);
//        log.info(distinctNodeIdsByTaskId.size());
//        for (String nodeId : distinctNodeIdsByTaskId) {
//            List<Relationship> correlatedRecords = relationshipRepository.findAllByStartNode_IdAndFromTaskId(nodeId, 6L);
//            log.info(correlatedRecords.size());
//        }

        ParseResult parseResult = relationshipParser.parse(Path.of("/Users/dominik/Downloads/20210930-gleif-concatenated-file-rr.xml").toFile());
        log.info(parseResult.getRelationships().size());
        log.info(parseResult.getNodeCorrelations().size());
    }


    private void scheduleTask(DataProcessingTask task) {
        try {
            task = handleFilePreparationAction(task);
        } catch (Exception e) {
            log.error("Problem with handle file preparation action. Setting status to FAILED for task ID: {}", task.getId(), e);
            updateProcessingTaskStatus(task, ProgressStatus.FILE_PROCESSING_FAILED);
            throw e;
        }
        try {
            task = handleFileProcessingAction(task);
        } catch (Exception e) {
            log.error("Problem with handle file processing action. Setting status to FAILED for task ID: {}", task.getId(), e);
            updateProcessingTaskStatus(task, ProgressStatus.DATA_IMPORT_FAILED);
            throw e;
        }
        try {
            task = handleDataProcessingAction(task);
        } catch (Exception e) {
            log.error("Problem with handle data processing action. Setting status to FAILED for task ID: {}", task.getId(), e);
            updateProcessingTaskStatus(task, ProgressStatus.DATA_PROCESSING_FAILED);
            throw e;
        }
    }


    private DataProcessingTask handleFilePreparationAction(DataProcessingTask task) {
        log.info("Start first file preparation actions.");
        Path zipFilePath = fileService.downloadFile();
        if (zipFilePath != null) {
            updateProcessingTaskTmpFilePath(task, zipFilePath);
            updateProcessingTaskStatus(task, ProgressStatus.FILE_DOWNLOAD_SUCCESS);
            Path unzippedFilePath = fileService.unzipFile(zipFilePath);
            if (unzippedFilePath != null) {
                updateProcessingTaskStatus(task, ProgressStatus.FILE_PROCESSING_PENDING);
                updateProcessingTaskTmpFilePath(task, unzippedFilePath);
            } else {
                updateProcessingTaskStatus(task, ProgressStatus.FILE_PROCESSING_FAILED);
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
        updateProcessingTaskStatus(task, ProgressStatus.DATA_IMPORT_PENDING);
        long startTime = System.nanoTime();
        ParseResult parseResult = relationshipParser.parse(Path.of(task.getTmpFilePath()).toFile());
        long estimatedTime = System.nanoTime() - startTime;
        double elapsedTimeInSecond = (double) estimatedTime / 1_000_000_000;
        log.info("Execution time: {} ns", estimatedTime);
        log.info("Execution time: {} s", elapsedTimeInSecond);
        log.info("Relationship size {}", parseResult.getRelationships().size());
        log.info("Node correlations size {}", parseResult.getNodeCorrelations().size());
        parseResult.getRelationships().forEach(el -> el.setFromTaskId(task.getId()));
        parseResult.getNodeCorrelations().forEach(el -> el.setFromTaskId(task.getId()));
        long startTimeForSave = System.nanoTime();
        relationshipRepository.saveAll(parseResult.getRelationships());
        long estimatedTimeForSave = System.nanoTime() - startTimeForSave;
        double elapsedTimeForSaveInSecond = (double) estimatedTimeForSave / 1_000_000_000;
        log.info("Save time relationships: {} ns", estimatedTimeForSave);
        log.info("Save time relationships: {} s", elapsedTimeForSaveInSecond);
        long startTimeForSaveCorrelations = System.nanoTime();
        nodeCorrelationRepository.saveAll(parseResult.getNodeCorrelations());
        long estimatedTimeForSaveCorrelations = System.nanoTime() - startTimeForSaveCorrelations;
        double elapsedTimeForSaveCorrelationsInSecond = (double) estimatedTimeForSaveCorrelations / 1_000_000_000;
        log.info("Save time Correlations: {} ns", estimatedTimeForSaveCorrelations);
        log.info("Save time Correlations: {} s", elapsedTimeForSaveCorrelationsInSecond);
        log.info("End file processing actions.");
        updateProcessingTaskStatus(task, ProgressStatus.DATA_IMPORT_SUCCESS);
        return task;
    }

    private Map<String, String> convertRelationshipsIntoMap(List<Relationship> relationships) {
        Map<String, String> resultMap = new HashMap<>();
        relationships.forEach(relationship -> {
            resultMap.put(relationship.getType(),relationship.getEndNode().getId());
        });
        return resultMap;
    }

    private DataProcessingTask handleDataProcessingAction(DataProcessingTask task) {
        log.info("Start data processing actions for task ID: {}", task.getId());
        updateProcessingTaskStatus(task, ProgressStatus.DATA_PROCESSING_PENDING);
        log.info("End data processing actions for task ID: {}", task.getId());
        updateProcessingTaskStatus(task, ProgressStatus.DATA_PROCESSING_SUCCESS);
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
