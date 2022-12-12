package com.jdw.springboot.spel.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class Society {
    private String name;
    public static String Advisors = "advisors";
    public static String President = "president";

    private final List<Inventor> members = new ArrayList<>();
    private final Map<String, Object> officers = new HashMap();


    public boolean isMember(String name) {
        for (Inventor inventor : members) {
            if (inventor.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }
}
