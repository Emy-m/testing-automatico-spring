package com.unrn.vv.crud;

import com.unrn.vv.crud.entity.Product;
import com.unrn.vv.crud.entity.Provider;
import com.unrn.vv.crud.repository.ProductRepository;
import com.unrn.vv.crud.repository.ProviderRepository;
import com.unrn.vv.crud.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ProductUnitTests {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProviderRepository providerRepository;

    @InjectMocks
    private ProductService productService;

    private Product product;
    private Provider provider;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        product = new Product("headset", 2, 7999);
        product.setId(1);
        
        provider = new Provider("Emilio", "2920112233", "Calle falsa 111");
        provider.setId(1);
    }

    @Test
    public void testAddProduct() {
        when(productRepository.save(any(Product.class))).thenReturn(product);
        
        Product savedProduct = productService.saveProduct(product);
        
        assertNotNull(savedProduct);
        assertEquals("headset", savedProduct.getName());
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    public void testGetAllProducts() {
        when(productRepository.findAll()).thenReturn(Arrays.asList(product));
        
        List<Product> products = productService.getProducts();
        
        assertEquals(1, products.size());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    public void testGetProductById() {
        when(productRepository.findById(1)).thenReturn(Optional.of(product));
        
        Product foundProduct = productService.getProductById(1);
        
        assertNotNull(foundProduct);
        assertEquals("headset", foundProduct.getName());
        verify(productRepository, times(1)).findById(1);
    }

    @Test
    public void testUpdateProduct() {
        when(productRepository.findById(1)).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(product);
        
        product.setPrice(1999);
        Product updatedProduct = productService.updateProduct(1, product);
        
        assertNotNull(updatedProduct);
        assertEquals(1999, updatedProduct.getPrice());
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    public void testDeleteProduct() {
        when(productRepository.findById(1)).thenReturn(Optional.of(product));
        doNothing().when(productRepository).deleteById(1);
        
        String result = productService.deleteProduct(1);
        
        assertEquals("product removed !! 1", result);
        verify(productRepository, times(1)).deleteById(1);
    }

    @Test
    public void testSetProviderForProduct() {
        when(productRepository.findById(1)).thenReturn(Optional.of(product));
        when(providerRepository.findById(1)).thenReturn(Optional.of(provider));
        when(productRepository.save(any(Product.class))).thenReturn(product);
        
        Product productWithProvider = productService.assignProvider(1, 1);
        
        assertNotNull(productWithProvider);
        assertNotNull(productWithProvider.getProvider());
        assertEquals("Emilio", productWithProvider.getProvider().getName());
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    public void testGetProviderForProduct() {
        product.setProvider(provider);
        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
        
        Provider productProvider = productService.getProvider(product.getId());
        
        assertNotNull(productProvider);
        assertEquals("Emilio", productProvider.getName());
        verify(productRepository, times(1)).findById(1);
    }
}
