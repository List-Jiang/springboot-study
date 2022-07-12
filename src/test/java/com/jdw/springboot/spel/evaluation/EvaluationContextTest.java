package com.jdw.springboot.spel.evaluation;

import org.junit.jupiter.api.Test;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.SpelCompilerMode;
import org.springframework.expression.spel.SpelParserConfiguration;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.SimpleEvaluationContext;

import java.util.ArrayList;
import java.util.List;

public class EvaluationContextTest {
    @Test
    public void simpleEvaluationContextTest() {
        class Simple {
            public final List<Boolean> booleanList = new ArrayList<>();
        }

        Simple simple = new Simple();
        simple.booleanList.add(true);

        EvaluationContext context = SimpleEvaluationContext.forReadOnlyDataBinding().build();

        // 设置值字符串 "false" ，发现需要的是 Boolean 类型的，就进行了类型转换变为布尔类型 false
        // will recognize that it needs to be a Boolean and convert it accordingly.
        ExpressionParser parser = new SpelExpressionParser();
        parser.parseExpression("booleanList[0]").setValue(context, simple, "false");

        // b is false
        Boolean b = simple.booleanList.get(0);
    }

    @Test
    public void spelParserConfigurationTest() {
        class Demo {
            public List<String> list;
        }

        // Turn on:
        // - 会自动调用默认初始化方法对相应的 null 进行初始化
        // - 集合大小自增
        SpelParserConfiguration config = new SpelParserConfiguration(true, true);

        ExpressionParser parser = new SpelExpressionParser(config);

        Expression expression = parser.parseExpression("list[8]");

        Demo demo = new Demo();
        // 此时 demo.list == null
        Object o = expression.getValue(demo);
        // 此时 demo.list = ["","","",""]
    }


    @Test
    public void spelCompilerConfigurationTest() {

        class MyMessage {
            public final String payload = "Hello world";
        }

        SpelParserConfiguration config = new SpelParserConfiguration(SpelCompilerMode.IMMEDIATE,
                this.getClass().getClassLoader());
        SpelExpressionParser parser = new SpelExpressionParser(config);
        Expression expr = parser.parseExpression("payload");
        MyMessage message = new MyMessage();
        Object payload = expr.getValue(message);
    }

}
