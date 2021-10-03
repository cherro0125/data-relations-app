package com.kaliszewski.datarelations.data.model.processing;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.OffsetDateTime;


@Entity
@Table(name = "processing_tasks")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataProcessingTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ProgressStatus progressStatus;

    private OffsetDateTime startTime;

    private OffsetDateTime endTime;

    @JsonIgnore
    private String tmpFilePath;
}