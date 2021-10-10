package com.kaliszewski.datarelations.service.relationship;

import java.util.Set;

public interface RelationshipService {
    Set<String> getDistinctNodeIdsByTaskId(Long taskId);
}
