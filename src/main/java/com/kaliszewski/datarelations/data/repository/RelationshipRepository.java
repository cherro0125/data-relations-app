package com.kaliszewski.datarelations.data.repository;

import com.kaliszewski.datarelations.data.model.reationship.Relationship;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface RelationshipRepository extends MongoRepository<Relationship, String> {
}
