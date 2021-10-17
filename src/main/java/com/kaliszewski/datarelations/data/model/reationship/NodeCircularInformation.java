package com.kaliszewski.datarelations.data.model.reationship;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@Data
@Document(collection = "node_circular_info")
@AllArgsConstructor
@NoArgsConstructor
public class NodeCircularInformation {
    @Id
    private String id;
    private String nodeName;
    private boolean isCircular;
    private Long fromTaskId;
}
