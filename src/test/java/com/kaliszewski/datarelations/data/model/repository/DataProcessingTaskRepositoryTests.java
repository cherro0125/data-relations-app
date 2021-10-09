package com.kaliszewski.datarelations.data.model.repository;


import com.kaliszewski.datarelations.data.model.processing.DataProcessingTask;
import com.kaliszewski.datarelations.data.model.processing.ProgressStatus;
import com.kaliszewski.datarelations.data.repository.DataProcessingTaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.OffsetDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
public class DataProcessingTaskRepositoryTests {

    @Autowired
    private DataProcessingTaskRepository dataProcessingTaskRepository;
    private DataProcessingTask task;

    @BeforeEach
    public void setup() {
        task = new DataProcessingTask();
        task.setStartTime(OffsetDateTime.now());
        task.setProgressStatus(ProgressStatus.DATA_PROCESSING_PENDING);
        task.setTmpFilePath("/test/path");

        task = dataProcessingTaskRepository.save(task);
    }

    @Test
    @DisplayName("Check is repository return correct task records")
    public void checkIsRepositoryReturnCorrectRecords() {
        Optional<DataProcessingTask> byId = dataProcessingTaskRepository.findById(task.getId());
        assertThat(byId)
                .isNotNull()
                .isPresent()
                .get()
                .isEqualTo(task);
    }
}
