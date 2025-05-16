package com.unrn.vv.crud;

import com.unrn.vv.crud.entity.Sale;
import com.unrn.vv.crud.repository.SaleRepository;
import com.unrn.vv.crud.service.SaleService;
import com.unrn.vv.crud.utils.enums.SaleStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class SaleUnitTests {

    @Mock
    private SaleRepository saleRepository;

    @InjectMocks
    private SaleService saleService;

    private Sale sale;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        sale = new Sale(LocalDate.now(), 100, SaleStatus.PENDING);
        sale.setId(1);
    }

    @Test
    public void testAddSale() {
        when(saleRepository.save(any(Sale.class))).thenReturn(sale);
        
        Sale savedSale = saleService.saveSale(sale);
        
        assertNotNull(savedSale);
        assertEquals(SaleStatus.PENDING, savedSale.getState());
        verify(saleRepository, times(1)).save(any(Sale.class));
    }

    @Test
    public void testCancelSale() {
        when(saleRepository.findById(1)).thenReturn(Optional.of(sale));
        when(saleRepository.save(any(Sale.class))).thenReturn(sale);
        
        Sale cancelledSale = saleService.updateStateSale(1, SaleStatus.CANCELLED);
        
        assertNotNull(cancelledSale);
        assertEquals(SaleStatus.CANCELLED, cancelledSale.getState());
        verify(saleRepository, times(1)).save(any(Sale.class));
    }

    @Test
    public void testPaySale() {
        when(saleRepository.findById(1)).thenReturn(Optional.of(sale));
        when(saleRepository.save(any(Sale.class))).thenReturn(sale);
        
        Sale paidSale = saleService.updateStateSale(1, SaleStatus.PAID);
        
        assertNotNull(paidSale);
        assertEquals(SaleStatus.PAID, paidSale.getState());
        verify(saleRepository, times(1)).save(any(Sale.class));
    }

    @Test
    public void testFinishSale() {
        when(saleRepository.findById(1)).thenReturn(Optional.of(sale));
        when(saleRepository.save(any(Sale.class))).thenReturn(sale);
        
        Sale finishedSale = saleService.updateStateSale(1, SaleStatus.FINALIZED);
        
        assertNotNull(finishedSale);
        assertEquals(SaleStatus.FINALIZED, finishedSale.getState());
        verify(saleRepository, times(1)).save(any(Sale.class));
    }
}
