package com.example.toolsleasing.model;

import jakarta.persistence.*;

@Entity(name = "tools")
public class CTool {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    public CTool() {
        this.name = "";
//        this.price = 0.0;
    }

    public CTool(String name, Double price) {
        this.name = name;
//        this.price = price;
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

//    public Double getPrice() {
//        return price;
//    }
//
//    public void setPrice(Double price) {
//        if (price>=0)
//            this.price = price;
//    }
}
