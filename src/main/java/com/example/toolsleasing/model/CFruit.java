package com.example.toolsleasing.model;

import jakarta.persistence.*;

@Entity(name = "fruit_store")
public class CFruit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column
    private String country;

    @Column
    private Double price;

    public CFruit() {
        this.name = "";
        this.country = "";
        this.price = 0.0;
    }

    public CFruit(Long id, String name, String country, Double price) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.price = price;
    }

    public CFruit(String name, String country, Double price) {
        this.name = name;
        this.country = country;
        this.price = price;
    }

    public CFruit(String name, Double price) {
        this.name = name;
        this.price = price;
    }

    public CFruit(String country) {
        this.country = country;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        if (price>=0)
            this.price = price;
    }
}
