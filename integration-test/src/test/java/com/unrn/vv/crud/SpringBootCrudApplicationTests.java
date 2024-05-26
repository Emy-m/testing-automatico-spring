package com.unrn.vv.crud;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import com.unrn.vv.crud.entity.Product;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SpringBootCrudApplicationTests {

    @LocalServerPort
    private int port;

    private String baseUrl = "http://localhost";

    private static RestTemplate restTemplate;


    @Autowired
    private TestH2Repository h2Repository;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeAll
    public static void init() {   

        restTemplate = new RestTemplate();
     
    }

    @BeforeEach
    public void setUp() {
        baseUrl = baseUrl.concat(":").concat(port + "").concat("/products");

        jdbcTemplate.execute("INSERT INTO products (id,name, quantity, price) VALUES (1,'CAR', 1, 334000)");
        jdbcTemplate.execute("INSERT INTO products (id,name, quantity, price) VALUES (2,'shoes', 1, 999)");
        jdbcTemplate.execute("INSERT INTO products (id,name, quantity, price) VALUES (4,'CAR', 1, 34000)");
        jdbcTemplate.execute("INSERT INTO products (id,name, quantity, price) VALUES (8,'books', 5, 1499)");
    }

    @Test
    public void testAddProduct() {
        Product product = new Product("headset", 2, 7999);
        Product response = restTemplate.postForObject(baseUrl, product, Product.class);
        assertEquals("headset", response.getName());
        assertEquals(5, h2Repository.findAll().size());
    }

    @Test
    public void testAddProduct2() {
       
        HttpEntity<Product> request = new HttpEntity<>(new Product("headset", 2, 7999));
        ResponseEntity<Product> response = restTemplate
          .exchange(baseUrl, HttpMethod.POST, request, Product.class);
         
        assertEquals( HttpStatus.CREATED, response.getStatusCode());
         
        Product prod = response.getBody();
         
        assertNotNull(prod);
        assertEquals("headset", prod.getName());
        assertEquals(5, h2Repository.findAll().size());
    }


    @Test
    public void testGetProducts() {     

        List<Product> products = restTemplate.getForObject(baseUrl, List.class);
        assertEquals(5, products.size());
        assertEquals(5, h2Repository.findAll().size());
    }


    @Test
    public void testFindProductById() {
        Product product = restTemplate.getForObject(baseUrl + "/{id}", Product.class, 1);
        assertAll(
                () -> assertNotNull(product),
                () -> assertEquals(1, product.getId()),
                () -> assertEquals("CAR", product.getName())
        );

    }

    @Test
    public void testUpdateProduct(){
        Product product = new Product("shoes", 1, 1999);
        restTemplate.put(baseUrl+"/update/{id}", product, 2);
        Product productFromDB = h2Repository.findById(2).get();
        assertAll(
                () -> assertNotNull(productFromDB),
                () -> assertEquals(1999, productFromDB.getPrice())
        );



    }

    @Test
    public void testDeleteProduct(){
        int recordCount=h2Repository.findAll().size();
        assertEquals(5, recordCount);
        restTemplate.delete(baseUrl+"/{id}", 8);
        assertEquals(4, h2Repository.findAll().size());

    }


}
