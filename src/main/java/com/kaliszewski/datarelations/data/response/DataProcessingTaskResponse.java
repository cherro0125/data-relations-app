package com.kaliszewski.datarelations.data.response;

import com.kaliszewski.datarelations.data.model.processing.ProgressStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataProcessingTaskResponse {
    private Long taskId;
    private ProgressStatus taskStatus;
}
