package com.kaliszewski.datarelations.web.controller.correlation;

import com.kaliszewski.datarelations.data.model.reationship.NodeCorrelation;
import com.kaliszewski.datarelations.data.response.DataMessageResponse;
import com.kaliszewski.datarelations.service.correlation.NodeCorrelationService;
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
@RequestMapping("/api/correlations")
@OpenAPIDefinition
@AllArgsConstructor
public class CorrelationsController {

    private NodeCorrelationService nodeCorrelationService;

    @Tag(name = "Correlations: All", description = "Get all correlations data from all task processing.")
    @GetMapping(path = "/all")
    public ResponseEntity<DataMessageResponse<List<NodeCorrelation>>> getAll() {
        return DataMessageResponse.createSimpleOk(nodeCorrelationService.getAll()).toResponseEntity();
    }

    @Tag(name = "Correlations: For node name using all task", description = "Get correlations data for node id from all task processing.")
    @GetMapping(path = "/all/{nodeName}")
    public ResponseEntity<DataMessageResponse<List<NodeCorrelation>>> getForNodeByAllTasks(@PathVariable String nodeName) {
        return DataMessageResponse.createSimpleOk(nodeCorrelationService.getByStartNodeName(nodeName)).toResponseEntity();
    }

    @Tag(name = "Correlations: latest", description = "Get correlations data using latest task data processing.")
    @GetMapping(path = "/latest")
    public ResponseEntity<DataMessageResponse<List<NodeCorrelation>>> getAllByLatestTask() {
        return DataMessageResponse.createSimpleOk(nodeCorrelationService.getAllByLatestTask()).toResponseEntity();
    }

    @Tag(name = "Correlations: Node correlation", description = "Get node correlations data using latest task data processing.")
    @GetMapping(path = "/latest/{nodeName}")
    public ResponseEntity<DataMessageResponse<NodeCorrelation>> getByNodeNameForLatestTask(@PathVariable String nodeName) {
        return DataMessageResponse.createSimpleOk(nodeCorrelationService.getByStartNodeForLatestTask(nodeName)).toResponseEntity();
    }

    @Tag(name = "Correlations: byTask", description = "Get correlations data using provided task data processing.")
    @GetMapping(path = "/byTask/{taskId}")
    public ResponseEntity<DataMessageResponse<List<NodeCorrelation>>> getAllByTask(@PathVariable Long taskId) {
        return DataMessageResponse.createSimpleOk(nodeCorrelationService.getAllByTaskId(taskId)).toResponseEntity();
    }

    @Tag(name = "Correlations: Node correlation by Task", description = "Get node correlations data using provided task data processing.")
    @GetMapping(path = "/byTask/{taskId}/{nodeName}")
    public ResponseEntity<DataMessageResponse<NodeCorrelation>> getByNodeNameForLatestTask(@PathVariable String nodeName, @PathVariable Long taskId) {
        return DataMessageResponse.createSimpleOk(nodeCorrelationService.getByTaskIdAndStarNodeName(nodeName, taskId)).toResponseEntity();
    }
}
