package com.unrn.vv.crud.repository;

import com.unrn.vv.crud.entity.Provider;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProviderRepository extends JpaRepository<Provider,Integer> {
    Provider findByName(String name);
}
