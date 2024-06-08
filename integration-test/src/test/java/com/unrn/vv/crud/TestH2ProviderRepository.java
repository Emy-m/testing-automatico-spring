package com.unrn.vv.crud;

import com.unrn.vv.crud.entity.Provider;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestH2ProviderRepository extends JpaRepository<Provider,Integer> {
}
