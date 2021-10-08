package com.kaliszewski.datarelations.parser;


import com.kaliszewski.datarelations.data.model.reationship.Relationship;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import static org.assertj.core.api.Assertions.anyOf;
import static org.assertj.core.api.Assertions.assertThat;



@RunWith(JUnit4.class)
public class RelationshipParserTests {


    private RelationshipParser relationshipParser;
    private File xmlFile;

    @Before
    public void setup() throws FileNotFoundException {
        relationshipParser = new RelationshipParserImpl();
        xmlFile = ResourceUtils.getFile(
                "classpath:test.xml");
    }

    @Test
    @DisplayName("Check is parser correct parse XML file to Relationship object list")
    public void test() {
        List<Relationship> relationships = relationshipParser.parse(xmlFile);
        assertThat(relationships).isNotNull();
        assertThat(relationships.size()).isEqualTo(3);
        assertThat(relationships).allMatch(r -> r.getStartNode() != null);
        assertThat(relationships).allMatch(r -> r.getEndNode() != null);
    }

}
