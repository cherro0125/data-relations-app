package com.kaliszewski.datarelations.web.controller.processing;

import com.kaliszewski.datarelations.service.processing.ProcessingService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@WebMvcTest
public class DataProcessingControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProcessingService processingService;


    public void test() {

    }

}
