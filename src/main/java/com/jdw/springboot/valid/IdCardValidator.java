package com.jdw.springboot.valid;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

public class IdCardValidator implements ConstraintValidator<IdCard, CharSequence> {
    private static final int[] POWERS = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
    // 余数对应的最后一位校验码
    private static final char[] CODE = {'1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'};

    private static final Pattern PROVINCIAL_PATTERN = Pattern.compile("1[1-5]|2[123]|3[1-7]|4[1-6]|5[0-4]|6[1-5]|71|81|82");
    private static final Pattern NUMERIC_PATTERN_15 = Pattern.compile("^\\d{15}$");
    private static final Pattern NUMERIC_PATTERN_17 = Pattern.compile("^\\d{17}$");
    private static final DateTimeFormatter yyyyMMdd = DateTimeFormatter.ofPattern("yyyyMMdd");


    @Override
    public void initialize(IdCard constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
        boolean state = false;
        try {
            if (PROVINCIAL_PATTERN.matcher(value.subSequence(0, 2)).matches()
                    && ((value.length() == 15 && NUMERIC_PATTERN_15.matcher(value.subSequence(0, 15)).matches())
                    || (value.length() == 18 && NUMERIC_PATTERN_17.matcher(value.subSequence(0, 17)).matches()))) {
                LocalDate.parse(value.subSequence(6, 14), yyyyMMdd);
                if (value.length() == 18) {
                    int sum = 0;
                    for (int i = 0; i < 17; i++) {
                        sum += Integer.parseInt(((String) value).substring(i, i + 1)) * POWERS[i];
                    }
                    state = CODE[sum % 11] == value.charAt(17);
                } else {
                    state = true;
                }
            }
        } catch (Exception e) {
            return false;
        }
        return state;
    }
}
