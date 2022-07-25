package com.jdw.springboot.faker;

import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import org.junit.jupiter.api.Test;

import java.util.Locale;

public class BaseTest {
    @Test
    public void name() {
        Faker faker = new Faker();
        final Name name = faker.name();
        System.out.println("firstName : " + name.firstName());
        System.out.println("username : " + name.username());
        System.out.println("bloodGroup : " + name.bloodGroup());
        System.out.println("suffix : " + name.suffix());
        System.out.println("title : " + name.title());
        System.out.println("lastName : " + name.lastName());
        System.out.println("nameWithMiddle : " + name.nameWithMiddle());
        System.out.println("fullName : " + name.fullName());
        System.out.println("name : " + name.name());
        System.out.println("prefix : " + name.prefix());
    }

    @Test
    public void localName() {
        Faker faker = new Faker(Locale.CHINA);
        final Name name = faker.name();
        System.out.println("firstName : " + name.firstName());
        System.out.println("username : " + name.username());
        System.out.println("bloodGroup : " + name.bloodGroup());
        System.out.println("suffix : " + name.suffix());
        System.out.println("title : " + name.title());
        System.out.println("lastName : " + name.lastName());
        System.out.println("nameWithMiddle : " + name.nameWithMiddle());
        System.out.println("fullName : " + name.fullName());
        System.out.println("name : " + name.name());
        System.out.println("prefix : " + name.prefix());
    }
}
