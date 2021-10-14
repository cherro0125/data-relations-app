package com.kaliszewski.datarelations.data.repository;

import com.kaliszewski.datarelations.data.model.reationship.NodeCorrelationStatistic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NodeCorrelationStatisticRepository extends JpaRepository<NodeCorrelationStatistic, Long> {
    void deleteAllByTaskId(Long taskId);
    List<NodeCorrelationStatistic> findAllByTaskId(Long taskId);
}
