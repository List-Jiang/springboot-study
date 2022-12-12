package com.jdw.springboot.spel.entity;

import lombok.Data;

@Data
public class PlaceOfBirth {

    private String city;
    private String country;

    public PlaceOfBirth(String city) {
        this.city = city;
    }
}
