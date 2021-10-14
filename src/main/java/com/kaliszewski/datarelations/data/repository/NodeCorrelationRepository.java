package com.kaliszewski.datarelations.data.repository;

import com.kaliszewski.datarelations.data.model.reationship.NodeCorrelation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface NodeCorrelationRepository extends MongoRepository<NodeCorrelation, String>, NodeCorrelationCustomRepository {
    List<NodeCorrelation> findAllByFromTaskId(Long taskId);
    List<NodeCorrelation> findByNodeName(String nodeName);
    Optional<NodeCorrelation> findByNodeNameAndFromTaskId(String nodeName, Long taskId);
}
