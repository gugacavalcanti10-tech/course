package com.educandoweb.course.resources;

import com.educandoweb.course.resources.exceptions.ResourceExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
class ResourceNotFoundTests {

    @Autowired
    private UserResource userResource;

    @Autowired
    private ProductResource productResource;

    @Autowired
    private CategoryResource categoryResource;

    @Autowired
    private OrderResource orderResource;

    private MockMvc mvc;

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders.standaloneSetup(userResource, productResource, categoryResource, orderResource)
                .setControllerAdvice(new ResourceExceptionHandler()).build();
    }

    @ParameterizedTest
    @ValueSource(strings = {"/users", "/products", "/categories", "/orders"})
    void missingResourceReturnsStandardError(String endpoint) throws Exception {
        String path = endpoint + "/" + Long.MAX_VALUE;
        mvc.perform(get(path))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Resource not found "))
                .andExpect(jsonPath("$.message").value("Resource not found. id " + Long.MAX_VALUE))
                .andExpect(jsonPath("$.path").value(path))
                .andExpect(jsonPath("$.timetamp").value(notNullValue()));
    }

    @ParameterizedTest
    @ValueSource(strings = {"/users", "/products", "/categories", "/orders"})
    @Transactional
    void existingResourceRemainsAccessible(String endpoint) throws Exception {
        mvc.perform(get(endpoint + "/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }
}
