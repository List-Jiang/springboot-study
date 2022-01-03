package com.jdw.sys.controller.test;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;

@RestController
@RequestMapping("/formatter")
public class FormatterController {

    @RequestMapping("/test1")
    BodyFormatterTest test(@RequestBody BodyFormatterTest bodyFormatterTest) {
        System.out.println(bodyFormatterTest);
        bodyFormatterTest.setDate(new Date());
        bodyFormatterTest.setLocalDate(LocalDate.now());
        bodyFormatterTest.setLocalDateTime(LocalDateTime.now());
        bodyFormatterTest.setLocalTime(LocalTime.now());
        bodyFormatterTest.setCalendar(Calendar.getInstance());
        return bodyFormatterTest;
    }
}
