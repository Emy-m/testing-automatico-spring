package com.unrn.vv.crud.repository;

import com.unrn.vv.crud.entity.Provider;
import org.springframework.data.jpa.repository.JpaRepository;

import com.unrn.vv.crud.entity.Product;

import java.util.List;


public interface ProductRepository extends JpaRepository<Product,Integer> {
    Product findByName(String name);

    List<Product> findByProvider(Provider provider);
}

