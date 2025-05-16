package com.unrn.vv.crud;

import com.unrn.vv.crud.entity.Provider;
import com.unrn.vv.crud.repository.ProviderRepository;
import com.unrn.vv.crud.service.ProviderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ProviderUnitTests {

    @Mock
    private ProviderRepository providerRepository;

    @InjectMocks
    private ProviderService providerService;

    private Provider provider;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        provider = new Provider("Emilio", "2920112233", "Calle falsa 111");
        provider.setId(1);
    }

    @Test
    public void testAddProvider() {
        when(providerRepository.save(any(Provider.class))).thenReturn(provider);
        
        Provider savedProvider = providerService.saveProvider(provider);
        
        assertNotNull(savedProvider);
        assertEquals("Emilio", savedProvider.getName());
        verify(providerRepository, times(1)).save(any(Provider.class));
    }

    @Test
    public void testUpdateProvider() {
        when(providerRepository.findById(1)).thenReturn(Optional.of(provider));
        when(providerRepository.save(any(Provider.class))).thenReturn(provider);
        
        provider.setName("Martin");
        Provider updatedProvider = providerService.updateProvider(1, provider);
        
        assertNotNull(updatedProvider);
        assertEquals("Martin", updatedProvider.getName());
        verify(providerRepository, times(1)).save(any(Provider.class));
    }

    @Test
    public void testDeleteProvider() {
        when(providerRepository.findById(1)).thenReturn(Optional.of(provider));
        doNothing().when(providerRepository).deleteById(1);
        
        String result = providerService.deleteProvider(1);
        
        assertEquals("1", result);
        verify(providerRepository, times(1)).deleteById(1);
    }
}
