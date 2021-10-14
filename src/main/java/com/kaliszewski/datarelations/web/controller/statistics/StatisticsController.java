package com.kaliszewski.datarelations.web.controller.statistics;

import com.kaliszewski.datarelations.data.model.reationship.NodeCorrelationStatistic;
import com.kaliszewski.datarelations.data.response.DataMessageResponse;
import com.kaliszewski.datarelations.service.correlation.NodeCorrelationStatisticService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Log4j2
@RequestMapping("/api/statistics")
@AllArgsConstructor
@OpenAPIDefinition
public class StatisticsController {

    private NodeCorrelationStatisticService nodeCorrelationStatisticService;


    @Tag(name = "Statistics: latest", description = "Get statistics data for correlated nodes using data from latest task.")
    @GetMapping("/latest")
    public ResponseEntity<DataMessageResponse<List<NodeCorrelationStatistic>>> getStatisticsForLatestTask() {
        return DataMessageResponse.createSimpleOk(nodeCorrelationStatisticService.getStatisticsForLatestTask()).toResponseEntity();
    }

    @Tag(name = "Statistics: byTaskId", description = "Get statistics data for correlated nodes using data from provided task.")
    @GetMapping("/{taskId}")
    public ResponseEntity<DataMessageResponse<List<NodeCorrelationStatistic>>> getStatisticsForTaskId(@PathVariable Long taskId) {
        return DataMessageResponse.createSimpleOk(nodeCorrelationStatisticService.getStatisticsByTaskId(taskId)).toResponseEntity();
    }

}
