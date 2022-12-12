package com.jdw.springboot.format;

import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;

@Log4j2
@RestController
@RequestMapping("/formatter")
public class FormatterController {

    @PostMapping("/post")
    BodyFormatter post(@Valid @RequestBody BodyFormatter bodyFormatterTest) {
        log.debug(bodyFormatterTest.toString());
        bodyFormatterTest.setDate(new Date());
        bodyFormatterTest.setLocalDate(LocalDate.now());
        bodyFormatterTest.setLocalDateTime(LocalDateTime.now());
        bodyFormatterTest.setLocalTime(LocalTime.now());
        bodyFormatterTest.setCalendar(Calendar.getInstance());
        return bodyFormatterTest;
    }

    @GetMapping("/get")
    BodyFormatter get(@Valid @ModelAttribute BodyFormatter bodyFormatterTest) {
        bodyFormatterTest.setDate(new Date());
        bodyFormatterTest.setLocalDate(LocalDate.now());
        bodyFormatterTest.setLocalDateTime(LocalDateTime.now());
        bodyFormatterTest.setLocalTime(LocalTime.now());
        bodyFormatterTest.setCalendar(Calendar.getInstance());
        return bodyFormatterTest;
    }
}