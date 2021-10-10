package com.kaliszewski.datarelations.data.model.reationship;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "node_correlations")
public class NodeCorrelation {
    @Id
    private String id;
    private String nodeName;
    private Map<String, List<String>> correlatedNodes = new HashMap<>();
    private Long fromTaskId;
}
