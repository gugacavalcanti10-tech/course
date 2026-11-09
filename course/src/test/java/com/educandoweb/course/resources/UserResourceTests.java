package com.educandoweb.course.resources;

import com.educandoweb.course.entities.User;
import com.educandoweb.course.repositories.UserRepository;
import com.educandoweb.course.resources.exceptions.ResourceExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class UserResourceTests {

    @Autowired
    private UserResource resource;

    @Autowired
    private UserRepository repository;

    @Autowired
    private JdbcTemplate jdbc;

    private MockMvc mvc;

    private static final String VALID_USER = """
            {"name":"Ana Atualizada","email":"ana@exemplo.com","phone":"11999999999","password":"Abcdefgh1!"}
            """;

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders.standaloneSetup(resource)
                .setControllerAdvice(new ResourceExceptionHandler()).build();
    }

    @Test
    void updatingMissingUserReturns404() throws Exception {
        mvc.perform(put("/users/{id}", Long.MAX_VALUE)
                        .contentType(MediaType.APPLICATION_JSON).content(VALID_USER))
                .andExpect(status().isNotFound());
        assertFalse(repository.existsById(Long.MAX_VALUE));
    }

    @Test
    void deletingMissingUserReturns404() throws Exception {
        mvc.perform(delete("/users/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void deletingUserWithOrdersReturns409AndPreservesUser() throws Exception {
        Long id = jdbc.queryForObject("select min(client_id) from tb_order", Long.class);
        mvc.perform(delete("/users/{id}", id)).andExpect(status().isConflict());
        assertTrue(repository.existsById(id));
    }

    @Test
    void updatesAndDeletesExistingUserWithoutOrders() throws Exception {
        User user = repository.save(new User("11999999999", "ana@exemplo.com", "Ana", "Abcdefgh1!"));
        Long id = user.getId();
        try {
            mvc.perform(put("/users/{id}", id)
                            .contentType(MediaType.APPLICATION_JSON).content(VALID_USER))
                    .andExpect(status().isOk());
            assertEquals("Ana Atualizada", repository.findById(id).orElseThrow().getName());
            mvc.perform(delete("/users/{id}", id)).andExpect(status().isNoContent());
            assertFalse(repository.existsById(id));
        } finally {
            repository.deleteById(id);
        }
    }
}
