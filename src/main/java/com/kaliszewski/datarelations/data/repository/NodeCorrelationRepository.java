package com.kaliszewski.datarelations.data.repository;

import com.kaliszewski.datarelations.data.model.reationship.NodeCorrelation;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NodeCorrelationRepository extends MongoRepository<NodeCorrelation, String> {
}
