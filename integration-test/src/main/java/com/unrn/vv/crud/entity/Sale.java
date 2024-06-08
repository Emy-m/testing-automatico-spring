package com.unrn.vv.crud.entity;

import com.unrn.vv.crud.utils.enums.SaleStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "sales")
public class Sale {

    @Id
    @GeneratedValue
    private int id;
    private LocalDate sale_date;
    private double total;

    @Enumerated(EnumType.STRING)
    private SaleStatus state;

    @ManyToMany
    @JoinTable(
        name = "sale_products",
        joinColumns = @JoinColumn(name = "sale_id"),
        inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private Set<Product> products;

    public Sale(LocalDate sale_date, double total, SaleStatus state) {
        this.sale_date = sale_date;
        this.total = total;
        this.state = state;
    }
}
