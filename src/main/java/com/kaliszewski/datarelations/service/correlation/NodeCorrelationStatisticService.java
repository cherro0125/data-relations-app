package com.kaliszewski.datarelations.service.correlation;

import com.kaliszewski.datarelations.data.model.reationship.NodeCorrelationStatistic;

import java.util.List;

public interface NodeCorrelationStatisticService {
    List<NodeCorrelationStatistic> addStatistics(List<NodeCorrelationStatistic> statisticList);
}
