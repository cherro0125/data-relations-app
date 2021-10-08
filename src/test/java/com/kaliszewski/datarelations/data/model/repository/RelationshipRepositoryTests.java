package com.kaliszewski.datarelations.data.model.repository;

import com.kaliszewski.datarelations.data.model.reationship.Node;
import com.kaliszewski.datarelations.data.model.reationship.Relationship;
import com.kaliszewski.datarelations.data.repository.RelationshipRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@Testcontainers
@DataMongoTest(excludeAutoConfiguration = EmbeddedMongoAutoConfiguration.class)
public class RelationshipRepositoryTests {

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.4.2");

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @Autowired
    private RelationshipRepository relationshipRepository;
    private String id;

    @BeforeEach
    public void setup() {
        relationshipRepository.deleteAll();

        Relationship relationship = new Relationship();
        relationship.setFromTaskId(1L);
        relationship.setType("TEST_TYPE");
        Node startNode = new Node();
        startNode.setType("START_TYPE");
        startNode.setId("T_011");
        Node endNode = new Node();
        startNode.setType("END_TYPE");
        startNode.setId("T_012");
        relationship.setStartNode(startNode);
        relationship.setEndNode(endNode);

        id = relationshipRepository.save(relationship).getId();
    }

    @Test
    public void test() {
        List<Relationship> relationships = relationshipRepository.findAll();
        assertThat(relationships).isNotNull();
        assertThat(relationships.size()).isEqualTo(1);
    }

}
