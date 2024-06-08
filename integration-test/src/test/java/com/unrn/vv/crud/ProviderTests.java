package com.unrn.vv.crud;

import com.unrn.vv.crud.entity.Provider;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProviderTests {
    @LocalServerPort
    private int port;

    private String baseUrl = "http://localhost";

    private static RestTemplate restTemplate;


    @Autowired
    private TestH2ProviderRepository h2Repository;

    @Autowired
    private MockMvc mvc;

    @BeforeAll
    public static void init() {
        restTemplate = new RestTemplate();
    }

    @BeforeEach
    public void setUp() {
        baseUrl = baseUrl.concat(":").concat(port + "").concat("/providers");
    }

    @Test
    @Sql(statements = "DELETE FROM providers", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void testAddProvider() {
        Provider provider = new Provider("Emilio", "2920112233", "Calle falsa 111");
        Provider response = restTemplate.postForObject(baseUrl, provider, Provider.class);
        assertNotNull(response);
        assertNotNull(response.getId());
        assertEquals("Emilio", response.getName());
        assertEquals(1, h2Repository.findAll().size());
    }

    @Test
    @Sql(statements = "DELETE FROM providers", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "INSERT INTO providers (id, name, phone, street) VALUES (1, 'Emilio', '2920112233', 'Calle falsa 111')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void testUpdateProvider() {
        Provider provider = new Provider("Martin", "2920112233", "Calle falsa 111");
        restTemplate.put(baseUrl + "/{id}", provider, 1);
        Provider response = h2Repository.findById(1).get();
        assertNotNull(response);
        assertEquals("Martin", response.getName());
        assertEquals(1, h2Repository.findAll().size());
    }

    @Test
    @Sql(statements = "DELETE FROM providers", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "INSERT INTO providers (id, name, phone, street) VALUES (1, 'Emilio', '2920112233', 'Calle falsa 111')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void testDeleteProvider() {
        assertEquals(1, h2Repository.findAll().size());
        restTemplate.delete(baseUrl + "/{id}", 1);
        assertEquals(0, h2Repository.findAll().size());
    }
}
