package com.unrn.vv.crud;

import com.unrn.vv.crud.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestH2SaleRepository extends JpaRepository<Sale,Integer> {
}
