package com.jdw.springboot.format;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;

@RestController
@RequestMapping("/formatter")
public class FormatterController {

    @PostMapping("/post")
    BodyFormatter post(@Valid @RequestBody BodyFormatter bodyFormatterTest) {
        System.out.println(bodyFormatterTest);
        bodyFormatterTest.setDate(new Date());
        bodyFormatterTest.setLocalDate(LocalDate.now());
        bodyFormatterTest.setLocalDateTime(LocalDateTime.now());
        bodyFormatterTest.setLocalTime(LocalTime.now());
        bodyFormatterTest.setCalendar(Calendar.getInstance());
        return bodyFormatterTest;
    }
    @GetMapping("/get")
    BodyFormatter get(@Valid @ModelAttribute BodyFormatter bodyFormatterTest) {
        System.out.println(bodyFormatterTest);
        bodyFormatterTest.setDate(new Date());
        bodyFormatterTest.setLocalDate(LocalDate.now());
        bodyFormatterTest.setLocalDateTime(LocalDateTime.now());
        bodyFormatterTest.setLocalTime(LocalTime.now());
        bodyFormatterTest.setCalendar(Calendar.getInstance());
        return bodyFormatterTest;
    }
}
