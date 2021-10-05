package com.kaliszewski.datarelations.parser;


import com.kaliszewski.datarelations.data.model.reationship.Node;
import com.kaliszewski.datarelations.data.model.reationship.Relationship;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
public class RelationshipParserImpl implements RelationshipParser {


    private final XMLInputFactory factory = XMLInputFactory.newInstance();


    @Override
    public List<Relationship> parse(File file) {
        List<Relationship> relationships = new ArrayList<>();
        try (final FileInputStream fileInputStream = new FileInputStream(file)) {
            final XMLEventReader reader = factory.createXMLEventReader(fileInputStream);
            while (reader.hasNext()) {
                final XMLEvent event = reader.nextEvent();
                if (event.isStartElement() && event.asStartElement().getName()
                        .getLocalPart().equals(XmlElements.RELATIONSHIP_ELEMENT)) {
                    Relationship relationship = parseRelationship(reader);
                    relationships.add(relationship);
                }
            }
        } catch (IOException | XMLStreamException e) {
            log.error("Something get wrong with file getting.", e);
        }
        return relationships;
    }

    private Relationship parseRelationship(final XMLEventReader reader) throws XMLStreamException {
        Relationship relationship = new Relationship();
        while (reader.hasNext()) {
            final XMLEvent event = reader.nextEvent();
            if (event.isEndElement() && event.asEndElement().getName().getLocalPart().equals(XmlElements.RELATIONSHIP_ELEMENT)) {
                break;
            }
            if (event.isStartElement()) {
                final StartElement element = event.asStartElement();
                final String elementName = element.getName().getLocalPart();
                switch (elementName) {
                    case XmlElements.NODE_TYPE_ELEMENT -> relationship.setType(reader.getElementText());
                    case XmlElements.START_NODE_ELEMENT -> relationship.setStartNode(parseNode(reader, XmlElements.START_NODE_ELEMENT));
                    case XmlElements.END_NODE_ELEMENT -> relationship.setEndNode(parseNode(reader, XmlElements.END_NODE_ELEMENT));
                }
            }
        }
        return relationship;
    }

    private Node parseNode(final XMLEventReader reader, String elementName) throws XMLStreamException {
        Node node = new Node();
        while (reader.hasNext()) {
            final XMLEvent event = reader.nextEvent();
            if (event.isEndElement() && event.asEndElement().getName().getLocalPart().equals(elementName)) {
                break;
            }
            if (event.isStartElement()) {
                final StartElement element = event.asStartElement();
                final String localElementName = element.getName().getLocalPart();
                switch (localElementName) {
                    case XmlElements.NODE_ID_ELEMENT -> node.setId(reader.getElementText());
                    case XmlElements.NODE_TYPE_ELEMENT -> node.setType(reader.getElementText());
                }
            }
        }
        return node;
    }
}
