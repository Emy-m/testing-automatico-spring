package com.unrn.vv.crud.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "providers")
public class Provider {

    @Id
    @GeneratedValue
    private int id;
    private String name;
    private String phone;
    private String street;

    public Provider(String name, String phone, String street) {
        this.name = name;
        this.phone = phone;
        this.street = street;
    }

}
