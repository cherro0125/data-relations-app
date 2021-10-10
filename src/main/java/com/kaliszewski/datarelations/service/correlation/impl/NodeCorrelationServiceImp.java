package com.kaliszewski.datarelations.service.correlation.impl;

import com.kaliszewski.datarelations.data.model.processing.DataProcessingTask;
import com.kaliszewski.datarelations.data.model.processing.ProgressStatus;
import com.kaliszewski.datarelations.data.model.reationship.NodeCorrelation;
import com.kaliszewski.datarelations.data.repository.DataProcessingTaskRepository;
import com.kaliszewski.datarelations.data.repository.NodeCorrelationRepository;
import com.kaliszewski.datarelations.service.correlation.NodeCorrelationService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Log4j2
@AllArgsConstructor
public class NodeCorrelationServiceImp implements NodeCorrelationService {

    private NodeCorrelationRepository nodeCorrelationRepository;
    private DataProcessingTaskRepository dataProcessingTaskRepository;

    @Override
    public List<NodeCorrelation> getAllByLatestTask() {
        Optional<DataProcessingTask> task = dataProcessingTaskRepository.findFirstByProgressStatusOrderByIdDesc(ProgressStatus.DATA_PROCESSING_SUCCESS);
        if(task.isPresent()) {
            return nodeCorrelationRepository.findAllByFromTaskId(task.get().getId());
        } else {
            log.info("Any record with status DATA_PROCESSING_SUCCESS not found.");
            return Collections.emptyList();
        }
    }

    @Override
    public NodeCorrelation getByStartNodeForLatestTask(String nodeName) {
        Optional<DataProcessingTask> task = dataProcessingTaskRepository.findFirstByProgressStatusOrderByIdDesc(ProgressStatus.DATA_PROCESSING_SUCCESS);
        if(task.isPresent()) {
            return nodeCorrelationRepository.findByNodeNameAndFromTaskId(nodeName,task.get().getId()).orElseThrow(EntityNotFoundException::new);
        } else {
            log.info("Any record with status DATA_PROCESSING_SUCCESS not found.");
            return null;
        }
    }

    @Override
    public List<NodeCorrelation> getAll() {
        return nodeCorrelationRepository.findAll();
    }

    @Override
    public List<NodeCorrelation> getAllByTaskId(Long taskId) {
        return nodeCorrelationRepository.findAllByFromTaskId(taskId);
    }

    @Override
    public List<NodeCorrelation> getByStartNodeName(String nodeName) {
        return nodeCorrelationRepository.findByNodeName(nodeName);
    }

    @Override
    public NodeCorrelation getByTaskIdAndStarNodeName(String nodeName, Long taskId) {
        return nodeCorrelationRepository.findByNodeNameAndFromTaskId(nodeName, taskId).orElseThrow(EntityNotFoundException::new);
    }
}
