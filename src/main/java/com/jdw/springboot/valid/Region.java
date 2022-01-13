package com.jdw.springboot.valid;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = RegionValidator.class)
public @interface Region {
    String[] regexps() default {};

    String message() default "非法的区划名称";

    /**
     * 将validator进行分类，不同的类group中会执行不同的validator操作
     *
     * @return validator的分类类型
     */
    Class<?>[] groups() default {};

    /**
     * 主要是针对bean，很少使用
     *
     * @return 负载
     */
    Class<? extends Payload>[] payload() default {};
}
