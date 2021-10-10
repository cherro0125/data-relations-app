package com.kaliszewski.datarelations.service.relationship.impl;

import com.kaliszewski.datarelations.data.model.reationship.Relationship;
import com.kaliszewski.datarelations.service.relationship.RelationshipService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Log4j2
@Service
@AllArgsConstructor
public class RelationshipServiceImpl implements RelationshipService {

    private MongoOperations mongoOperations;

    @Override
    public Set<String> getDistinctNodeIdsByTaskId(Long taskId) {
        List<String> startNodeIds = getDistinctStartNodeIdsByTaskId(taskId);
        List<String> endNodeIds = getDistinctEndNodeIdsByTaskId(taskId);
        Set<String> nodeIds = new HashSet<>(startNodeIds);
        nodeIds.addAll(endNodeIds);
        return nodeIds;
    }

    private List<String> getDistinctStartNodeIdsByTaskId(Long taskId) {
        Query query = new Query(Criteria.where("fromTaskId").is(taskId));
        return mongoOperations.findDistinct(query, "startNode._id", Relationship.class, String.class);
    }

    private List<String> getDistinctEndNodeIdsByTaskId(Long taskId) {
        Query query = new Query(Criteria.where("fromTaskId").is(taskId));
        return mongoOperations.findDistinct(query, "endNode._id", Relationship.class, String.class);
    }
}
