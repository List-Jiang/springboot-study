package com.jdw.springboot.spel;

import com.jdw.springboot.spel.entity.Inventor;
import org.junit.Test;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import java.util.GregorianCalendar;

public class SpELTests {
    @Test
    public void SpELTest() {
        ExpressionParser parser = new SpelExpressionParser();
        Expression exp = parser.parseExpression("'Hello World'");
        // 简单字符串
        String message = (String) exp.getValue();
        assert message.equals("Hello World");
        // 拼接字符串
        exp = parser.parseExpression("'Hello World'.concat('!')");
        message = (String) exp.getValue();
        assert message.equals("Hello World!");
        // 获取字符串长度
        exp = parser.parseExpression("'Hello World'.bytes.length");
        assert exp.getValue().equals(11);
        // 调用 String 构造函数，再将结构字符串转大写
        exp = parser.parseExpression("new String('hello world').toUpperCase()");
        message = exp.getValue(String.class);
        assert message.equals("HELLO WORLD");
    }

    @Test
    public void SpELObjectTest() {
        GregorianCalendar c = new GregorianCalendar();
        c.set(1856, 7, 9);
        // The constructor arguments are name, birthday, and nationality.
        Inventor tesla = new Inventor("Nikola Tesla", c.getTime(), "Serbian");

        ExpressionParser parser = new SpelExpressionParser();

        Expression exp = parser.parseExpression("name"); // Parse name as an expression
        assert "Nikola Tesla".equals(exp.getValue(tesla, String.class));
        // name == "Nikola Tesla"

        exp = parser.parseExpression("name == 'Nikola Tesla'");
        assert exp.getValue(tesla, Boolean.class);
        // result == true
    }
}
