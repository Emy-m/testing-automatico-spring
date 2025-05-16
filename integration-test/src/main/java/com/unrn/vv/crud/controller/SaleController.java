package com.unrn.vv.crud.controller;

import com.unrn.vv.crud.entity.Sale;
import com.unrn.vv.crud.service.SaleService;
import com.unrn.vv.crud.utils.enums.SaleStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/sales")
public class SaleController {
    @Autowired
    private SaleService service;

    @PostMapping
    public Sale addSale(@RequestBody Sale sale, HttpServletResponse response) {
        sale = service.saveSale(sale);
        response.setStatus(HttpServletResponse.SC_CREATED);
        return sale;
    }

    @GetMapping
    public List<Sale> findAllSales() {
        return service.getSales();
    }

    @GetMapping("/{id}")
    public Sale findSaleById(@PathVariable int id) {
        return service.getSaleById(id);
    }

    @PutMapping("/cancel/{id}")
    public Sale cancelSale(@PathVariable int id) {
        return service.updateStateSale(id, SaleStatus.CANCELLED);
    }

    @PutMapping("/pay/{id}")
    public Sale paySale(@PathVariable int id) {
        return service.updateStateSale(id, SaleStatus.PAID);
    }

    @PutMapping("/finish/{id}")
    public Sale finishSale(@PathVariable int id) {
        return service.updateStateSale(id, SaleStatus.FINALIZED);
    }
}
