package com.kaliszewski.datarelations.parser;


import com.kaliszewski.datarelations.data.model.reationship.Relationship;
import com.kaliszewski.datarelations.data.parser.ParseResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;




@SpringBootTest
public class RelationshipParserTests {


    @Autowired
    private RelationshipParser relationshipParser;
    private File xmlFile;

    @BeforeEach
    public void setup() throws FileNotFoundException {
        xmlFile = ResourceUtils.getFile(
                "classpath:test.xml");
    }

    @Test
    @DisplayName("Check is parser correct parse XML file to Relationship object list")
    public void parserTest() {
        ParseResult parseResult = relationshipParser.parse(xmlFile);
        assertThat(parseResult).isNotNull();
        assertThat(parseResult.getRelationships()).isNotNull();
        assertThat(parseResult.getNodeCorrelations()).isNotNull();
        assertThat(parseResult.getRelationships().size()).isEqualTo(3);
        assertThat(parseResult.getRelationships()).allMatch(r -> r.getStartNode() != null);
        assertThat(parseResult.getRelationships()).allMatch(r -> r.getEndNode() != null);
    }

}
