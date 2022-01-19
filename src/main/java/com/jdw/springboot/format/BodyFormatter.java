package com.jdw.springboot.format;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jdw.springboot.format.serializer.MoneyDeSerializer;
import com.jdw.springboot.format.serializer.MoneySerializer;
import com.jdw.springboot.valid.Region;
import lombok.Data;
import org.springframework.format.annotation.NumberFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;

@Data
public class BodyFormatter {
    private LocalDate localDate;
    private LocalDateTime localDateTime;
    private LocalTime localTime;
    private Date date;
    private Calendar calendar;

    @NumberFormat(pattern = "#.###")
    private BigDecimal bigDecimal1;
    @NumberFormat(style = NumberFormat.Style.CURRENCY)
    private BigDecimal bigDecimal2;
    @JsonSerialize(using = MoneySerializer.class)
    @JsonDeserialize(using = MoneyDeSerializer.class)
    private BigDecimal money;

    // 中文字符取值范围 [\u4E00-\u9FA5]
    // 省份包含直辖市
    @Region(regexps = "^[\u4E00-\u9FA5]*?省|[\u4E00-\u9FA5]*?行政区|[\u4E00-\u9FA5]*?市$", message = "非法的省份名称")
    private String province;
    @Region(regexps = "^[\u4E00-\u9FA5]*?市$", message = "非法的城市名称")
    private String city;
    @Region(regexps = "^[\u4E00-\u9FA5]*?[区,县]$", message = "非法的区县名称")
    private String county;
}