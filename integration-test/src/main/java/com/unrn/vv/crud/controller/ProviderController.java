package com.unrn.vv.crud.controller;

import com.unrn.vv.crud.entity.Provider;
import com.unrn.vv.crud.service.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/providers")
public class ProviderController {
    @Autowired
    private ProviderService service;

    // add provider
    @PostMapping
    public Provider addProvider(@RequestBody Provider provider, HttpServletResponse response) {
        provider = service.saveProvider(provider);
        response.setStatus(HttpServletResponse.SC_CREATED);
        return provider;
    }

    // get all providers
    @GetMapping
    public List<Provider> findAllProviders() {
        return service.getProviders();
    }

    // get provider by id
    @GetMapping("/{id}")
    public Provider findProviderById(@PathVariable int id) {
        return service.getProviderById(id);
    }

    // update provider
    @PutMapping("/{id}")
    public Provider updateProvider(@RequestBody Provider provider, @PathVariable int id) {
        return service.updateProvider(id, provider);
    }

    // delete provider
    @DeleteMapping("/{id}")
    public String deleteProvider(@PathVariable int id) {
        return service.deleteProvider(id);
    }
}
