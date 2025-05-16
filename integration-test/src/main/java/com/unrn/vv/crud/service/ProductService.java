package com.unrn.vv.crud.service;

import com.unrn.vv.crud.entity.Provider;
import com.unrn.vv.crud.repository.ProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unrn.vv.crud.entity.Product;
import com.unrn.vv.crud.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository repository;

    @Autowired
    private ProviderRepository providerRepository;

    public Product saveProduct(Product product) {
        return repository.save(product);
    }

    public List<Product> saveProducts(List<Product> products) {
        return repository.saveAll(products);
    }

    public List<Product> getProducts() {
        return repository.findAll();
    }

    public Product getProductById(int id) {
        return repository.findById(id).orElse(null);
    }

    public Product getProductByName(String name) {
        return repository.findByName(name);
    }

    public String deleteProduct(int id) {
        repository.deleteById(id);
        return "product removed !! " + id;
    }

    public Product updateProduct(int productId,Product product) {
        Product existingProduct = repository.findById(productId).orElse(null);
        existingProduct.setName(product.getName());
        existingProduct.setQuantity(product.getQuantity());
        existingProduct.setPrice(product.getPrice());
        return repository.save(existingProduct);
    }

    public List<Product> getProductsByProvider(int providerId) {
        Provider provider = providerRepository.findById(providerId).orElseThrow(
            () -> new RuntimeException("Provider not found with id: " + providerId)
        );
        return repository.findByProvider(provider);
    }

    public Product assignProvider(int productId, int providerId) {
        Product product = repository.findById(productId).orElseThrow(
            () -> new RuntimeException("Product not found with id: " + productId)
        );

        if (product.getProvider() != null) {
            throw new IllegalStateException("El producto ya tiene un proveedor asignado");
        }


        Provider provider = providerRepository.findById(providerId).orElseThrow(
            () -> new RuntimeException("Provider not found with id: " + providerId)
        );

        product.setProvider(provider);

        return repository.save(product);
    }

    public Provider getProvider(int productId) {
        Product product = repository.findById(productId).
                orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));
        return product.getProvider();
    }
}
