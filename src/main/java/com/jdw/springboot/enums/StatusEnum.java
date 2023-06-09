package com.jdw.springboot.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 状态值枚举
 *
 * @author 蒋德文
 * @since 2020/5/2714:01
 */
public enum StatusEnum implements IEnum<Integer> {
    EFFECTIVE(1, "有效"),
    INVALID(0, "无效");

    StatusEnum(Integer value, String explain) {
        this.value = value;
    }

    @JsonValue
    private final Integer value;

    @Override
    public Integer getValue() {
        return this.value;
    }

}
