package com.jdw.springboot.spel.evaluation;

import com.jdw.springboot.spel.entity.Inventor;
import com.jdw.springboot.spel.entity.Society;
import org.junit.jupiter.api.Test;
import org.springframework.expression.AccessException;
import org.springframework.expression.BeanResolver;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.SimpleEvaluationContext;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 基础 SpEL 测试
 * 官网地址：https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#expressions-language-ref
 */
public class ExpressParseTest {
    ExpressionParser parser = new SpelExpressionParser();
    EvaluationContext context = SimpleEvaluationContext.forReadOnlyDataBinding().build();

    @Test
    public void BasicStringTest() {
        ExpressionParser parser = new SpelExpressionParser();

        // evals to "Hello World"
        var helloWorld = (String) parser.parseExpression("'Hello World'").getValue();

        double avogadrosNumber = (Double) parser.parseExpression("6.0221415E+23").getValue();

        // evals to 2147483647
        var maxValue = (Integer) parser.parseExpression("0x7FFFFFFF").getValue();

        boolean trueValue = (Boolean) parser.parseExpression("true").getValue();

        var nullValue = parser.parseExpression("null").getValue();
    }

    @Test
    public void listMapTest() {
        ExpressionParser parser = new SpelExpressionParser();
        EvaluationContext context = SimpleEvaluationContext.forReadOnlyDataBinding().build();

        List numbers = (List) parser.parseExpression("{1,2,3,4}").getValue(context);

        List listOfLists = (List) parser.parseExpression("{{'a','b'},{'x','y'}}").getValue(context);

        // evaluates to a Java map containing the two entries
        Map inventorInfo = (Map) parser.parseExpression("{'name':'Nikola','d.ob':'10-July-1856'}").getValue(context);

        Map mapOfMaps = (Map) parser.parseExpression("{name:{first:'Nikola',last:'Tesla'},dob:{day:10,month:'July',year:1856}}").getValue(context);

        int[] numbers1 = (int[]) parser.parseExpression("new int[4]").getValue(context);

        // Array with initializer
        int[] numbers2 = (int[]) parser.parseExpression("new int[]{1,2,3}").getValue(context);

        // Multi dimensional array
        int[][] numbers3 = (int[][]) parser.parseExpression("new int[4][5]").getValue(context);
    }

    @Test
    public void RelationalOperatorsTest() {
        // evaluates to true
        boolean trueValue = parser.parseExpression("2 == 2").getValue(Boolean.class);

        // evaluates to false
        boolean falseValue = parser.parseExpression("2 < -5.0").getValue(Boolean.class);
        int a1 = 7 % 4;
        int a2 = -7 % 4;
        int a3 = 7 % -4;
        int a4 = -7 % -4;
        int b = 7 % 4;
        // evaluates to true
        boolean trueValue2 = parser.parseExpression("'black' < 'block'").getValue(Boolean.class);
    }

    @Test
    public void MathOperatorsTest() {
        // Addition
        int two = parser.parseExpression("1 + 1").getValue(Integer.class);  // 2

        String testString = parser.parseExpression(
                "'test' + ' ' + 'string'").getValue(String.class);  // 'test string'

        // Subtraction
        int four = parser.parseExpression("1 - -3").getValue(Integer.class);  // 4

        double d = parser.parseExpression("1000.00 - 1e4").getValue(Double.class);  // -9000

        // Multiplication
        int six = parser.parseExpression("-2 * -3").getValue(Integer.class);  // 6

        double twentyFour = parser.parseExpression("2.0 * 3e0 * 4").getValue(Double.class);  // 24.0

        // Division
        int minusTwo = parser.parseExpression("6 / -3").getValue(Integer.class);  // -2

        double one = parser.parseExpression("8.0 / 4e0 / 2").getValue(Double.class);  // 1.0

        // Modulus
        int three = parser.parseExpression("7 % 4").getValue(Integer.class);  // 3

        int one1 = parser.parseExpression("8 / 5 % 2").getValue(Integer.class);  // 1

        // Operator precedence
        int minusTwentyOne = parser.parseExpression("1+2-3*8").getValue(Integer.class);  // -21
    }


    public static String reverseString(String input) {
        StringBuilder backwards = new StringBuilder(input.length());
        for (int i = 0; i < input.length(); i++) {
            backwards.append(input.charAt(input.length() - 1 - i));
        }
        return backwards.toString();
    }

    @Test
    public void functionTest() throws NoSuchMethodException {

        ExpressionParser parser = new SpelExpressionParser();

        EvaluationContext context = SimpleEvaluationContext.forReadOnlyDataBinding().build();
        context.setVariable("reverseString",
                ExpressParseTest.class.getDeclaredMethod("reverseString", String.class));

        String helloWorldReversed = parser.parseExpression(
                "#reverseString('hello')").getValue(context, String.class);
    }

    @Test
    public void beanReferencesTest() {
        class MyBeanResolver implements BeanResolver {
            @Override
            public Object resolve(EvaluationContext context, String beanName) throws AccessException {
                if (beanName.equals("testBean"))
                    return new int[]{1, 2};
                return null;
            }
        }
        ExpressionParser parser = new SpelExpressionParser();
        StandardEvaluationContext context = new StandardEvaluationContext();
        context.setBeanResolver(new MyBeanResolver());

        // This will end up calling resolve(context,"something") on MyBeanResolver during evaluation
        Object bean = parser.parseExpression("@testBean").getValue(context);
        // This will end up calling resolve(context,"&foo") on MyBeanResolver during evaluation
        Object factoryBean = parser.parseExpression("&foo").getValue(context);
    }

    @Test
    public void test() {
        class Simple {
            public final List<Boolean> booleanList = new ArrayList<Boolean>();
        }

        Simple simple = new Simple();
        simple.booleanList.add(true);

        EvaluationContext context = SimpleEvaluationContext.forReadOnlyDataBinding().build();

// "false" is passed in here as a String. SpEL and the conversion service
// will recognize that it needs to be a Boolean and convert it accordingly.
        parser.parseExpression("booleanList[0]").setValue(context, simple, "false");

        // b is false
        Boolean b = simple.booleanList.get(0);
    }

    @Test
    public void ternaryOperatorTest() {
        ExpressionParser parser = new SpelExpressionParser();
        String falseString = parser.parseExpression(
                "false ? 'trueExp' : 'falseExp'").getValue(String.class);

        StandardEvaluationContext context = new StandardEvaluationContext();
        Society tesla = new Society();
        context.setRootObject(tesla);
        parser.parseExpression("name").setValue(context, "IEEE");
        context.setVariable("queryName", "Nikola Tesla");

        String expression = "isMember(#queryName)? #queryName + ' is a member of the ' " +
                "+ Name + ' Society' : #queryName + ' is not a member of the ' + Name + ' Society'";

        String queryResultString = parser.parseExpression(expression)
                .getValue(context, tesla, String.class);
        // queryResultString = "Nikola Tesla is not a member of the IEEE Society"
    }

    @Test
    public void ElvisOperatorTest() {
        ExpressionParser parser = new SpelExpressionParser();
        EvaluationContext context = SimpleEvaluationContext.forReadOnlyDataBinding().build();

        Inventor tesla = new Inventor("Nikola Tesla", "Serbian");
        String name = parser.parseExpression("name?:'Elvis Presley'").getValue(context, tesla, String.class);
        System.out.println(name);  // Nikola Tesla

        tesla.setName(null);
        // "name?:'Elvis Presley'"  等价于 "name!=null?name:'Elvis Presley'"
        name = parser.parseExpression("name?:'Elvis Presley'").getValue(context, tesla, String.class);
        System.out.println(name);  // Elvis Presley
    }
}
