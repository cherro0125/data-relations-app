package com.kaliszewski.datarelations.web.controller.processing;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kaliszewski.datarelations.data.model.processing.DataProcessingTask;
import com.kaliszewski.datarelations.data.model.processing.ProgressStatus;
import com.kaliszewski.datarelations.data.response.DataMessageResponse;
import com.kaliszewski.datarelations.data.response.MessageResponse;
import com.kaliszewski.datarelations.service.processing.ProcessingService;
import com.kaliszewski.datarelations.service.processing.impl.ProcessingServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityNotFoundException;
import java.time.OffsetDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class DataProcessingControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean(classes = ProcessingServiceImpl.class)
    private ProcessingService processingService;

    private final ObjectMapper ob = new ObjectMapper().registerModule(new JavaTimeModule());


    @Test
    @DisplayName("Check is get status controller method handle entity not found exception.")
    public void notExistingRecordTest() throws Exception {
        when(processingService.getTaskById(any())).thenThrow(new EntityNotFoundException());
        MessageResponse messageResponse = new MessageResponse("Entity not found.", HttpStatus.NOT_FOUND);
        mockMvc.perform(
                get("/api/processing/status/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ob.writeValueAsString(messageResponse)))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Check is get status controller method return existing entity.")
    public void returnExistingRecordsTest() throws Exception {
        DataProcessingTask task = new DataProcessingTask(1L, ProgressStatus.DATA_PROCESSING_PENDING,
                OffsetDateTime.now(), OffsetDateTime.now().plusMinutes(10),"/tmp");
        when(processingService.getTaskById(any())).thenReturn(task);
        DataMessageResponse<DataProcessingTask> response = DataMessageResponse.createSimpleOk(task);
        mockMvc.perform(
                        get("/api/processing/status/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(ob.writeValueAsString(response)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Check is init controller method handle entity not found exception.")
    public void notExistingRecordInitTest() throws Exception {
        when(processingService.initProcessing()).thenReturn(1L);
        when(processingService.getTaskById(1L)).thenThrow(new EntityNotFoundException());
        MessageResponse messageResponse = new MessageResponse("Entity not found.", HttpStatus.NOT_FOUND);
        mockMvc.perform(
                        post("/api/processing/init")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(ob.writeValueAsString(messageResponse)))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Check is init controller method return correct entity.")
    public void existingRecordInitTest() throws Exception {
        DataProcessingTask task = new DataProcessingTask(1L, ProgressStatus.DATA_PROCESSING_PENDING,
                OffsetDateTime.now(), OffsetDateTime.now().plusMinutes(10),"/tmp");
        when(processingService.initProcessing()).thenReturn(1L);
        when(processingService.getTaskById(1L)).thenReturn(task);
        DataMessageResponse<DataProcessingTask> response = DataMessageResponse.createSimpleOk(task);
        mockMvc.perform(
                        post("/api/processing/init")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(ob.writeValueAsString(response)))
                .andExpect(status().isOk());
    }

}
