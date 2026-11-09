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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;

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
    void createsReadsUpdatesAndDeletesUserUsingDTOs() throws Exception {
        String location = mvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON)
                        .content(VALID_USER.replace("{", "{\"id\":9223372036854775807,")))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.password").doesNotExist())
                .andExpect(jsonPath("$.orders").doesNotExist())
                .andReturn().getResponse().getHeader("Location");
        Long id = Long.valueOf(location.substring(location.lastIndexOf('/') + 1));
        try {
            assertNotEquals(Long.MAX_VALUE, id);
            mvc.perform(get("/users/{id}", id)).andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(id))
                    .andExpect(jsonPath("$.name").value("Ana Atualizada"))
                    .andExpect(jsonPath("$.email").value("ana@exemplo.com"))
                    .andExpect(jsonPath("$.phone").value("11999999999"))
                    .andExpect(jsonPath("$.password").doesNotExist())
                    .andExpect(jsonPath("$.orders").doesNotExist());
            mvc.perform(get("/users")).andExpect(status().isOk())
                    .andExpect(jsonPath("$[*].password").isEmpty())
                    .andExpect(jsonPath("$[*].orders").isEmpty());
            String update = """
                    {"name":"Ana Nova","email":"nova@exemplo.com","phone":"1133334444"}
                    """;
            mvc.perform(put("/users/{id}", id).contentType(MediaType.APPLICATION_JSON).content(update))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.name").value("Ana Nova"))
                    .andExpect(jsonPath("$.password").doesNotExist());
            mvc.perform(put("/users/{id}", id).contentType(MediaType.APPLICATION_JSON)
                            .content(update.replace("{", "{\"id\":9223372036854775807,\"password\":\"changed\",")))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(id))
                    .andExpect(jsonPath("$.password").doesNotExist());
            User persisted = repository.findById(id).orElseThrow();
            assertEquals("Abcdefgh1!", persisted.getPassword());
            assertEquals("nova@exemplo.com", persisted.getEmail());
            assertEquals("1133334444", persisted.getPhone());
            mvc.perform(delete("/users/{id}", id)).andExpect(status().isNoContent());
            mvc.perform(get("/users/{id}", id)).andExpect(status().isNotFound());
        } finally {
            repository.deleteById(id);
        }
    }

    @Test
    void validatesCreateAndUpdateDTOs() throws Exception {
        for (String invalid : new String[]{"{}", VALID_USER.replace("ana@exemplo.com", "invalid"),
                VALID_USER.replace("11999999999", "123"), VALID_USER.replace("Ana Atualizada", " ")}) {
            mvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON).content(invalid))
                    .andExpect(status().isBadRequest());
            mvc.perform(put("/users/{id}", Long.MAX_VALUE)
                            .contentType(MediaType.APPLICATION_JSON).content(invalid))
                    .andExpect(status().isBadRequest());
        }
        for (String password : new String[]{"", "Abcdefg1!", "abcdefgh1!", "Abcdefghi!", "Abcdefghi1"}) {
            mvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON)
                            .content(VALID_USER.replace("Abcdefgh1!", password)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.error").exists());
        }
        mvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Ana\",\"email\":\"ana@exemplo.com\",\"phone\":\"11999999999\"}"))
                .andExpect(status().isBadRequest());
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
