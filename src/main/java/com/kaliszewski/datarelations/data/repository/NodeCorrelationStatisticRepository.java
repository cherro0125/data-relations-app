package com.kaliszewski.datarelations.data.repository;

import com.kaliszewski.datarelations.data.model.reationship.NodeCorrelationStatistic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NodeCorrelationStatisticRepository extends JpaRepository<NodeCorrelationStatistic, Long> {
    void deleteAllByTaskId(Long taskId);
}
