package com.kaliszewski.datarelations.service.correlation;

import com.kaliszewski.datarelations.data.model.reationship.NodeCorrelation;

import java.util.List;

public interface NodeCorrelationService {
    List<NodeCorrelation> getAllByLatestTask();
    NodeCorrelation getByStartNodeForLatestTask(String nodeName);
    List<NodeCorrelation> getAll();
    List<NodeCorrelation> getAllByTaskId(Long taskId);
    List<NodeCorrelation> getByStartNodeName(String nodeName);
    NodeCorrelation getByTaskIdAndStarNodeName(String nodeName, Long taskId);
}
