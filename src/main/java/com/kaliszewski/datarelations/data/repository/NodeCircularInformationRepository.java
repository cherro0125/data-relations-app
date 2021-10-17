package com.kaliszewski.datarelations.data.repository;

import com.kaliszewski.datarelations.data.model.reationship.NodeCircularInformation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface NodeCircularInformationRepository extends MongoRepository<NodeCircularInformation, String> {
    List<NodeCircularInformation> findAllByFromTaskIdAndIsCircular(Long taskId, boolean isCircular);
}
