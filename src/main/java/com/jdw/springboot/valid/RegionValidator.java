package com.jdw.springboot.valid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

/**
 * Region：需要校验的自定义注解
 * CharSequence：需要校验的数据类型
 */
public class RegionValidator implements ConstraintValidator<Region, CharSequence> {
    private java.util.regex.Pattern[] patterns;

    @Override
    public void initialize(Region region) {
        patterns = new Pattern[region.regexps().length];
        for (int i = 0; i < region.regexps().length; i++) {
            patterns[i] = Pattern.compile(region.regexps()[i]);
        }
    }
    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        for (Pattern pattern : patterns) {
            if (pattern.matcher(value).matches()) {
                return true;
            }
        }

        return false;
    }
}
