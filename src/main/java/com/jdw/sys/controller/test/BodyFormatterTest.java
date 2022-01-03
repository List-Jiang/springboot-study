package com.jdw.sys.controller.test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;

public class BodyFormatterTest {
    private LocalDate localDate;
    private LocalDateTime localDateTime;
    private LocalTime localTime;
    private Date date;
    private Calendar calendar;

    public BodyFormatterTest() {
    }

    public BodyFormatterTest(LocalDate localDate, LocalDateTime localDateTime, LocalTime localTime, Date date, Calendar calendar) {
        this.localDate = localDate;
        this.localDateTime = localDateTime;
        this.localTime = localTime;
        this.date = date;
        this.calendar = calendar;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public LocalTime getLocalTime() {
        return localTime;
    }

    public void setLocalTime(LocalTime localTime) {
        this.localTime = localTime;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }

    @Override
    public String toString() {
        return "BodyFormatterTest{" +
                "localDate=" + localDate +
                ", localDateTime=" + localDateTime +
                ", localTime=" + localTime +
                ", date=" + date +
                ", calendar=" + calendar +
                '}';
    }
}
