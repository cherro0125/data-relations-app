package com.kaliszewski.datarelations.parser;

import com.kaliszewski.datarelations.data.model.reationship.Relationship;
import com.kaliszewski.datarelations.data.parser.ParseResult;

import java.io.File;
import java.util.List;

public interface RelationshipParser {
    ParseResult parse(File file);
}
