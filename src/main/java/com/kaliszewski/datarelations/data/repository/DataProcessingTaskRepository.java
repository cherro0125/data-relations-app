package com.kaliszewski.datarelations.data.repository;

import com.kaliszewski.datarelations.data.model.processing.DataProcessingTask;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DataProcessingTaskRepository extends JpaRepository<DataProcessingTask, Long> {
}