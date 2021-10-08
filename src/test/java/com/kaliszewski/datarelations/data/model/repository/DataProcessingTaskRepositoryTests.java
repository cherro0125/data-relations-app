package com.kaliszewski.datarelations.data.model.repository;


import com.kaliszewski.datarelations.data.model.processing.DataProcessingTask;
import com.kaliszewski.datarelations.data.model.processing.ProgressStatus;
import com.kaliszewski.datarelations.data.repository.DataProcessingTaskRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.OffsetDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
public class DataProcessingTaskRepositoryTests {

    @Autowired
    private DataProcessingTaskRepository dataProcessingTaskRepository;

    @Test
    public void test() {
        DataProcessingTask saveTask = new DataProcessingTask();
        OffsetDateTime now = OffsetDateTime.now();
        saveTask.setStartTime(now);
        saveTask.setProgressStatus(ProgressStatus.DATA_PROCESSING_PENDING);
        saveTask.setTmpFilePath("/test/path");

        Long id = dataProcessingTaskRepository.save(saveTask).getId();
        Optional<DataProcessingTask> byId = dataProcessingTaskRepository.findById(id);
        assertThat(byId).hasValueSatisfying( task -> {
            assertThat(task.getId()).isNotNull();
            assertThat(task.getId()).isEqualTo(id);
            assertThat(task.getProgressStatus()).isEqualTo(ProgressStatus.DATA_PROCESSING_PENDING);
            assertThat(task.getStartTime()).isEqualTo(now);
        });
    }
}
