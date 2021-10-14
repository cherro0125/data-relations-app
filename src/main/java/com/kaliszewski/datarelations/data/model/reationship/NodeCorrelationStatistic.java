package com.kaliszewski.datarelations.data.model.reationship;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@ToString
@Entity
@Table(name = "node_correlations_statistics")
@NoArgsConstructor
public class NodeCorrelationStatistic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String type;
    private Long count;
    private BigDecimal avg;
    private Long min;
    private Long max;
    private Long taskId;
}
