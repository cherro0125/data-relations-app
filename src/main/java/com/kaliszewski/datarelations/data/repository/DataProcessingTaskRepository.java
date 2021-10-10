package com.kaliszewski.datarelations.data.repository;

import com.kaliszewski.datarelations.data.model.processing.DataProcessingTask;
import com.kaliszewski.datarelations.data.model.processing.ProgressStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DataProcessingTaskRepository extends JpaRepository<DataProcessingTask, Long> {
    Optional<DataProcessingTask> findFirstByProgressStatusOrderByIdDesc(ProgressStatus progressStatus);
}