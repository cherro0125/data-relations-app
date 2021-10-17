package com.kaliszewski.datarelations.data.repository.impl;

import com.kaliszewski.datarelations.data.model.reationship.NodeCircularInformation;
import com.kaliszewski.datarelations.data.model.reationship.NodeCorrelation;
import com.kaliszewski.datarelations.data.model.reationship.NodeCorrelationStatistic;
import com.kaliszewski.datarelations.data.repository.NodeCorrelationCustomRepository;
import lombok.AllArgsConstructor;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.Collections;
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

    @Override
    public List<NodeCircularInformation> getCircularDependencies(Long taskId) {


        AggregationExpression reduce = aggregationOperationContext -> {
            Document inReduce = new Document("input", "$$this.nodes")
                    .append("initialValue", 0)
                    .append("in", new Document("$or", Arrays.asList("$$value", new Document("$eq", Arrays.asList("$this", "$nodeName")))));
            Document reduce2 = new Document("input", "$nodeChain.correlatedNodes")
                    .append("initialValue", 0)
                    .append("in", new Document("$or", Arrays.asList("$$value", new Document("$reduce", inReduce))));
            return new Document("$reduce", reduce2);
        };

        Aggregation agg = newAggregation(
                match(Criteria.where("fromTaskId").is(taskId)),
                unwind("correlatedNodes"),
                unwind("correlatedNodes.nodes"),
                graphLookup("node_correlations")
                        .startWith("correlatedNodes.nodes")
                        .connectFrom("correlatedNodes.nodes")
                        .connectTo("nodeName")
                        .depthField("depth")
                        .restrict(Criteria.where("nodeName").ne("nodeName").and("fromTaskId").is(taskId))
                        .as("nodeChain"),
                unwind("nodeChain"),
                addFields().addFieldWithValue("isCircular", reduce).build(),
//                ,
//                match(Criteria.where("isCircular").is(true)),
                project("nodeName", "isCircular", "fromTaskId")
                        .andExclude("_id"),
                out("node_circular_info")
        ).withOptions(AggregationOptions.builder().skipOutput().allowDiskUse(true).build());

//        AggregationResults<NodeCircularInformation> result = mongoTemplate.aggregate(agg, NodeCorrelation.class, NodeCircularInformation.class);
        mongoTemplate.aggregateStream(agg,NodeCorrelation.class, NodeCircularInformation.class);
//        List<NodeCircularInformation> mappedResults = result.getMappedResults();

//        return mappedResults;
        return Collections.emptyList();
    }
}
