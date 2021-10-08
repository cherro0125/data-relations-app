package com.kaliszewski.datarelations.service.processing;

import com.kaliszewski.datarelations.data.model.processing.DataProcessingTask;

public interface ProcessingService {
    Long initProcessing();

    DataProcessingTask getTaskById(Long taskId);

    void test();
}
