package com.jdw.springboot.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * @author ListJiang
 * @enum 性别枚举类
 * @remark
 * @date 2020/5/2710:15
 */
@Getter
public enum SexEnum {
    MAN(1, "男"), WOMAN(2, "女");

    SexEnum(int code, String sex) {
        this.code = code;
        this.sex = sex;
    }

    @EnumValue
    private final int code;
    @JsonValue
    private final String sex;
}
