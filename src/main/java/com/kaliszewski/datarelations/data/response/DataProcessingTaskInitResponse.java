package com.kaliszewski.datarelations.data.response;

import com.kaliszewski.datarelations.data.model.processing.ProgressStatus;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@RequiredArgsConstructor
public class DataProcessingTaskInitResponse extends DataProcessingTaskResponse {
    private String description;

    public DataProcessingTaskInitResponse(Long taskId, ProgressStatus taskStatus, String description) {
        super(taskId, taskStatus);
        this.description = description;
    }
}
