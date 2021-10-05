package com.kaliszewski.datarelations.parser;

import com.kaliszewski.datarelations.data.model.reationship.Relationship;

import java.io.File;
import java.util.List;

public interface RelationshipParser {
    List<Relationship> parse(File file);
}
