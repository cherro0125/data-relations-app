package com.kaliszewski.datarelations.data.repository;

import com.kaliszewski.datarelations.data.model.reationship.NodeCorrelationStatistic;

import java.util.List;

public interface NodeCorrelationCustomRepository {
    List<NodeCorrelationStatistic> getStatisticDataForCorrelationsByTaskId(Long taskId);
}
