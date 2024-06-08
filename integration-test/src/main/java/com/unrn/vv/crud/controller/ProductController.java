package com.unrn.vv.crud.controller;

import com.unrn.vv.crud.entity.Provider;
import lombok.extern.log4j.Log4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.unrn.vv.crud.entity.Product;
import com.unrn.vv.crud.service.ProductService;

import java.net.URI;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService service;

    @PostMapping
    public Product addProduct(@RequestBody Product product, HttpServletResponse response) {
        
        product = service.saveProduct(product);
        response.setStatus(HttpServletResponse.SC_CREATED);
        return product;
    }


    @GetMapping
    public List<Product> findAllProducts(HttpServletResponse response) {

        return service.getProducts();
    }

    @GetMapping("/{id}")
    public Product findProductById(@PathVariable int id) {
        return service.getProductById(id);
    }

    @PutMapping("/update/{id}")
    public Product updateProduct(@RequestBody Product product, @PathVariable int id) {
        return service.updateProduct(id, product);
    }

    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable int id) {
        return service.deleteProduct(id);
    }

    @GetMapping("/{id}/provider")
    public Provider getProductProvider(@PathVariable int id) {
        return service.getProvider(id);
    }

    @PutMapping("{id}/provider/{providerId}")
    public Product addProviderToProduct(@PathVariable int id, @PathVariable int providerId) {
        return service.assignProvider(id, providerId);
    }
}
