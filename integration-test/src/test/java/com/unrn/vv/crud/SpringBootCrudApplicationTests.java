package com.unrn.vv.crud;

import com.unrn.vv.crud.entity.Provider;
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
import org.springframework.test.context.jdbc.Sql;
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

    @BeforeAll
    public static void init() {
        restTemplate = new RestTemplate();
    }

    @BeforeEach
    public void setUp() {
        baseUrl = baseUrl.concat(":").concat(port + "").concat("/products");
    }

    @Test
    @Sql(statements = "DELETE FROM products", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void testAddProduct() {
        Product product = new Product("headset", 2, 7999);
        Product response = restTemplate.postForObject(baseUrl, product, Product.class);
        assertEquals("headset", response.getName());
        assertEquals(1, h2Repository.findAll().size());
    }

    @Test
    @Sql(statements = "DELETE FROM products", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void testAddProduct2() {
       
        HttpEntity<Product> request = new HttpEntity<>(new Product("headset", 2, 7999));
        ResponseEntity<Product> response = restTemplate
          .exchange(baseUrl, HttpMethod.POST, request, Product.class);
         
        assertEquals( HttpStatus.CREATED, response.getStatusCode());
         
        Product prod = response.getBody();
         
        assertNotNull(prod);
        assertEquals("headset", prod.getName());
        assertEquals(1, h2Repository.findAll().size());
    }


    @Test
    @Sql(statements = "DELETE FROM products", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "INSERT INTO products (id, name, quantity, price) VALUES (3, 'CAR', 1, 1000)")
    public void testGetProducts() {     

        List<Product> products = restTemplate.getForObject(baseUrl, List.class);
        assertEquals(1, products.size());
        assertEquals(1, h2Repository.findAll().size());
    }


    @Test
    @Sql(statements = "DELETE FROM products", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "INSERT INTO products (id, name, quantity, price) VALUES (4, 'CAR', 1, 1500)")
    public void testFindProductById() {
        Product product = restTemplate.getForObject(baseUrl + "/{id}", Product.class, 4);
        assertAll(
                () -> assertNotNull(product),
                () -> assertEquals(4, product.getId()),
                () -> assertEquals("CAR", product.getName())
        );

    }

    @Test
    @Sql(statements = "DELETE FROM products", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "INSERT INTO products (id, name, quantity, price) VALUES (2, 'shoes', 1, 999)")
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
    @Sql(statements = "DELETE FROM products", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "INSERT INTO products (id, name, quantity, price) VALUES (9, 'tshirt', 1, 200)")
    @Sql(statements = "INSERT INTO products (id, name, quantity, price) VALUES (8, 'shoes', 1, 999)")
    public void testDeleteProduct(){
        int recordCount=h2Repository.findAll().size();
        assertEquals(2, recordCount);
        restTemplate.delete(baseUrl+"/{id}", 8);
        assertEquals(1, h2Repository.findAll().size());

    }

    @Test
    @Sql(statements = "DELETE FROM products", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM providers", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "INSERT INTO products (id, name, quantity, price) VALUES (9, 'tshirt', 1, 200)")
    @Sql(statements = "INSERT INTO providers (id, name, phone, street) VALUES (1, 'Emilio', '2920112233', 'Calle falsa 111')")
    public void testSetProviderForProduct() {
        Product product = h2Repository.findById(9).get();
        assertNull(product.getProvider());

        restTemplate.put(baseUrl + "/{id}/provider/{providerId}", null, 9, 1);
        Product afterProduct = h2Repository.findById(9).get();
        assertNotNull(afterProduct.getProvider());
        assertEquals("Emilio", afterProduct.getProvider().getName());
    }

    @Test
    @Sql(statements = "DELETE FROM products", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM providers", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "INSERT INTO products (id, name, quantity, price) VALUES (9, 'tshirt', 1, 200)")
    @Sql(statements = "INSERT INTO providers (id, name, phone, street) VALUES (1, 'Emilio', '2920112233', 'Calle falsa 111')")
    public void testGetProviderForProduct() {
        restTemplate.put(baseUrl + "/{id}/provider/{providerId}", null, 9, 1);
        Provider provider = restTemplate.getForObject(baseUrl + "/{id}/provider", Provider.class, 9);
        assertNotNull(provider);
        assertEquals("Emilio", provider.getName());
    }
}
