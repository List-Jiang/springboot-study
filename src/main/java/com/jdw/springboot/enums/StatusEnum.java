package com.jdw.springboot.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author ListJiang
 * @enum 状态值枚举
 * @remark
 * @date 2020/5/2714:01
 */
public enum StatusEnum implements IEnum<Integer> {
    EFFECTIVE(1,"有效"),
    INVALID(0,"无效");

    StatusEnum(Integer value,String explain){
        this.value = value;
        this.explain = explain;
    }

    @JsonValue
    private final Integer value;
    private final String explain;

    @Override
    public Integer getValue() {
        return this.value;
    }

}
