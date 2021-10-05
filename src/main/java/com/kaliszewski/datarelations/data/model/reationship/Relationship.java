package com.kaliszewski.datarelations.data.model.reationship;

import lombok.Data;

@Data
public class Relationship {
    private Node startNode;
    private Node endNode;
    private String type;
}
