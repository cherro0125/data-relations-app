package com.kaliszewski.datarelations.service.correlation.impl;

import com.kaliszewski.datarelations.data.model.reationship.NodeCorrelationStatistic;
import com.kaliszewski.datarelations.data.repository.NodeCorrelationStatisticRepository;
import com.kaliszewski.datarelations.service.correlation.NodeCorrelationStatisticService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Log4j2
@AllArgsConstructor
public class NodeCorrelationStatisticServiceImpl implements NodeCorrelationStatisticService {

    private NodeCorrelationStatisticRepository nodeCorrelationStatisticRepository;

    @Override
    public List<NodeCorrelationStatistic> addStatistics(List<NodeCorrelationStatistic> statisticList) {
        Optional<Long> taskId = statisticList.stream().map(NodeCorrelationStatistic::getTaskId).filter(Objects::nonNull).findFirst();
        taskId.ifPresentOrElse(nodeCorrelationStatisticRepository::deleteAllByTaskId,() -> log.error("Provided statistics list with no task ID"));
        return nodeCorrelationStatisticRepository.saveAll(statisticList);
    }
}
