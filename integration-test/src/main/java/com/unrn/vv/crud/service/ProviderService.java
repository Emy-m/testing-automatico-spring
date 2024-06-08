package com.unrn.vv.crud.service;

import com.unrn.vv.crud.entity.Provider;
import com.unrn.vv.crud.repository.ProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProviderService {
    @Autowired
    private ProviderRepository repository;

    public Provider saveProvider(Provider provider) {
        return repository.save(provider);
    }

    public Provider updateProvider(int providerId, Provider provider) {
        Provider existingProvider = repository.findById(providerId).orElseThrow(
            () -> new RuntimeException("Provider not found with id: " + providerId)
        );
        existingProvider.setName(provider.getName());
        existingProvider.setPhone(provider.getPhone());
        existingProvider.setStreet(provider.getStreet());
        return repository.save(existingProvider);
    }

    public String deleteProvider(int id) {
        repository.deleteById(id);
        return String.valueOf(id);
    }

    public List<Provider> getProviders() {
        return repository.findAll();
    }

    public Provider getProviderById(int id) {
        return repository.findById(id).orElse(null);
    }
}
