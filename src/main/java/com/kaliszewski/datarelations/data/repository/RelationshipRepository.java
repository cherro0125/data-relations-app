package com.kaliszewski.datarelations.data.repository;

import com.kaliszewski.datarelations.data.model.reationship.Relationship;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;


public interface RelationshipRepository extends MongoRepository<Relationship, String> {
}
