package com.kaliszewski.datarelations.data.model.reationship;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "node_correlations")
public class NodeCorrelation {
    @Id
    private String id;
    private String nodeName;
    private List<NodeConnection> correlatedNodes = new ArrayList<>();
    private Long fromTaskId;
}
