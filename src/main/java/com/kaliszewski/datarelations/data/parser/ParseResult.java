package com.kaliszewski.datarelations.data.parser;

import com.kaliszewski.datarelations.data.model.reationship.NodeCorrelation;
import com.kaliszewski.datarelations.data.model.reationship.Relationship;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ParseResult {
    private List<NodeCorrelation> nodeCorrelations;
    private List<Relationship> relationships;
}
