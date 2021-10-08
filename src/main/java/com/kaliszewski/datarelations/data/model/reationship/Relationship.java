package com.kaliszewski.datarelations.data.model.reationship;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "relationships")
public class Relationship {
    @Id
    private String id;
    private Node startNode;
    private Node endNode;
    private String type;
    private Long fromTaskId;
}
