package com.unrn.vv.crud;

import com.unrn.vv.crud.entity.Sale;
import com.unrn.vv.crud.utils.enums.SaleStatus;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SaleTests {
    @LocalServerPort
    private int port;

    private String baseUrl = "http://localhost";

    private static RestTemplate restTemplate;


    @Autowired
    private TestH2SaleRepository h2Repository;

    @Autowired
    private MockMvc mvc;

    @BeforeAll
    public static void init() {
        restTemplate = new RestTemplate();
    }

    @BeforeEach
    public void setUp() {
        baseUrl = baseUrl.concat(":").concat(port + "").concat("/sales");
    }

    @Test
    @Sql(statements = "DELETE FROM sales", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM products", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "INSERT INTO products (id, name, quantity, price) VALUES (1, 'product1', 10, 100)", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void testAddSale() {
        Sale sale = new Sale(LocalDate.now(), 100, SaleStatus.PENDING);
        Sale response = restTemplate.postForObject(baseUrl, sale, Sale.class);
        assertNotNull(response);
        assertNotNull(response.getId());
        assertEquals(SaleStatus.PENDING, response.getState());
        assertEquals(1, h2Repository.findAll().size());
    }

    @Test
    @Sql(statements = "DELETE FROM sales", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "INSERT INTO sales (id, sale_date, total, state) VALUES (1, '2021-01-01', 100, 'PENDING')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void testCancelSale() {
        restTemplate.put(baseUrl + "/cancel/{id}", null, 1);
        Sale response = restTemplate.getForObject(baseUrl + "/{id}", Sale.class, 1);
        assertNotNull(response);
        assertEquals(SaleStatus.CANCELED, response.getState());
    }

    @Test
    @Sql(statements = "DELETE FROM sales", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "INSERT INTO sales (id, sale_date, total, state) VALUES (1, '2021-01-01', 100, 'PENDING')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void testPaySale() {
        restTemplate.put(baseUrl + "/pay/{id}", null, 1);
        Sale response = restTemplate.getForObject(baseUrl + "/{id}", Sale.class, 1);
        assertNotNull(response);
        assertEquals(SaleStatus.PAID, response.getState());
    }

    @Test
    @Sql(statements = "DELETE FROM sales", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "INSERT INTO sales (id, sale_date, total, state) VALUES (1, '2021-01-01', 100, 'PENDING')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void testFinishSale() {
        restTemplate.put(baseUrl + "/finish/{id}", null, 1);
        Sale response = restTemplate.getForObject(baseUrl + "/{id}", Sale.class, 1);
        assertNotNull(response);
        assertEquals(SaleStatus.FINALIZED, response.getState());
    }
}
