package com.kaliszewski.datarelations.service.correlation.impl;

import com.kaliszewski.datarelations.data.model.processing.DataProcessingTask;
import com.kaliszewski.datarelations.data.model.processing.ProgressStatus;
import com.kaliszewski.datarelations.data.model.reationship.NodeCorrelationStatistic;
import com.kaliszewski.datarelations.data.repository.DataProcessingTaskRepository;
import com.kaliszewski.datarelations.data.repository.NodeCorrelationStatisticRepository;
import com.kaliszewski.datarelations.service.correlation.NodeCorrelationStatisticService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Log4j2
@AllArgsConstructor
public class NodeCorrelationStatisticServiceImpl implements NodeCorrelationStatisticService {

    private NodeCorrelationStatisticRepository nodeCorrelationStatisticRepository;
    private DataProcessingTaskRepository dataProcessingTaskRepository;

    @Override
    public List<NodeCorrelationStatistic> addStatistics(List<NodeCorrelationStatistic> statisticList) {
        Optional<Long> taskId = statisticList.stream().map(NodeCorrelationStatistic::getTaskId).filter(Objects::nonNull).findFirst();
        taskId.ifPresentOrElse(nodeCorrelationStatisticRepository::deleteAllByTaskId,() -> log.error("Provided statistics list with no task ID"));
        return nodeCorrelationStatisticRepository.saveAll(statisticList);
    }

    @Override
    public List<NodeCorrelationStatistic> getStatisticsByTaskId(Long taskId) {
        return nodeCorrelationStatisticRepository.findAllByTaskId(taskId);
    }

    @Override
    public List<NodeCorrelationStatistic> getStatisticsForLatestTask() {
        Optional<DataProcessingTask> task = dataProcessingTaskRepository.findFirstByProgressStatusOrderByIdDesc(ProgressStatus.DATA_PROCESSING_SUCCESS);
        if(task.isPresent()) {
            return nodeCorrelationStatisticRepository.findAllByTaskId(task.get().getId());
        }
        log.error("Statistics not found due to not founded any task with status DATA_PROCESSING_SUCCESS.");
        return Collections.emptyList();
    }
}
