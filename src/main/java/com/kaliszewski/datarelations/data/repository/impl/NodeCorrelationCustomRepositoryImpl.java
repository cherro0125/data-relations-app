package com.kaliszewski.datarelations.data.repository.impl;

import com.kaliszewski.datarelations.data.model.reationship.NodeCorrelation;
import com.kaliszewski.datarelations.data.model.reationship.NodeCorrelationStatistic;
import com.kaliszewski.datarelations.data.repository.NodeCorrelationCustomRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;


@Repository
@AllArgsConstructor
public class NodeCorrelationCustomRepositoryImpl implements NodeCorrelationCustomRepository {

    private final MongoTemplate mongoTemplate;

    @Override
    public List<NodeCorrelationStatistic> getStatisticDataForCorrelationsByTaskId(Long taskId) {
        Aggregation agg = newAggregation(
                match(Criteria.where("fromTaskId").is(taskId)),
                unwind("correlatedNodes"),
                group("correlatedNodes.type")
                        .avg(ArrayOperators.Size.lengthOfArray("correlatedNodes.nodes")).as("avg")
                        .min(ArrayOperators.Size.lengthOfArray("correlatedNodes.nodes")).as("min")
                        .max(ArrayOperators.Size.lengthOfArray("correlatedNodes.nodes")).as("max")
                        .sum(ArrayOperators.Size.lengthOfArray("correlatedNodes.nodes")).as("count"),
                project("count", "avg", "min", "max")
                        .andExpression("_id").as("type")
                        .andExclude("_id")
        );

        AggregationResults<NodeCorrelationStatistic> groupResults = mongoTemplate.aggregate(agg, NodeCorrelation.class, NodeCorrelationStatistic.class);
        List<NodeCorrelationStatistic> mappedResults = groupResults.getMappedResults();
        mappedResults.forEach(el -> el.setTaskId(taskId));

        return mappedResults;
    }
}
