package com.unrn.vv.crud.service;

import com.unrn.vv.crud.repository.SaleRepository;
import com.unrn.vv.crud.utils.enums.SaleStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.unrn.vv.crud.entity.Sale;

import java.util.List;

@Service
public class SaleService {
    @Autowired
    private SaleRepository repository;

    public Sale saveSale(Sale sale) {
        return repository.save(sale);
    }

    public Sale updateStateSale(int saleId, SaleStatus status) {
        Sale existingSale = repository.findById(saleId).orElseThrow(
            () -> new RuntimeException("Sale not found with id: " + saleId)
        );

        existingSale.setState(status);

        return repository.save(existingSale);
    }

    public String deleteSale(int id) {
        repository.deleteById(id);
        return String.valueOf(id);
    }

    public List<Sale> getSales() {
        return repository.findAll();
    }

    public Sale getSaleById(int id) {
        return repository.findById(id).orElse(null);
    }
}
