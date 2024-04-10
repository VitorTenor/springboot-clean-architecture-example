package com.vitortenorio.springbootcleanarchitectureexample.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vitortenorio.springbootcleanarchitectureexample.SpringbootCleanArchitectureExampleApplication;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@AutoConfigureMockMvc
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = SpringbootCleanArchitectureExampleApplication.class)
public class AbstractContextTest {
    @Autowired
    private ObjectMapper jsonMapper;
    @Autowired
    protected MockMvc mockMvc;

    public PayloadExtractor payloadExtractor;


    @BeforeEach
    public void beforeTest(WebApplicationContext context) {
        MockitoAnnotations.openMocks(this);
        this.payloadExtractor = new PayloadExtractor(jsonMapper);
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }


    protected final String ONE_OR_MORE_FIELDS_ARE_INVALID = "One or more fields are invalid. Fill in correctly and try again.";

}
