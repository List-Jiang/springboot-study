package com.jdw.springboot;

import org.junit.jupiter.api.Test;

import java.util.function.*;

/**
 * @author ListJiang
 * @class jdk8函数式接口测试
 * @remark
 * @date 2020/12/16 19:24
 */
public class LambdaTest {

    /**
     * 断言
     */
    @Test
    public void Predicate() {
        Predicate<String> isHz = t -> {
            System.out.println("判断参数为：" + t);
            return t.equals("Hz");
        };
        System.out.println(isHz.test("Hz"));
    }

    /**
     * 生产者
     */
    @Test
    public void SupplierTest() {
        Supplier<String> getString = () -> {
            System.out.println("开始生产随机数字");
            return Double.toString(Math.ceil(Math.random()));
        };
        System.out.println(getString.get());
    }

    /**
     * 消费者
     */
    @Test
    public void ConsumerTest() {
        Consumer isEnd = (t) -> {
            System.out.println("开始消费" + t);
            System.out.println("开始消费" + t);
        };
        isEnd.accept("答题");
    }

    /**
     * 入参、出参不同类型
     */
    @Test
    public void FunctionTestCode() {
        Function<Integer, Boolean> function = (t) -> {
            System.out.println("入参:" + t);
            return t.equals("1");
        };
        System.out.println("入参:" + function.apply(1));
    }

    /**
     * 两个入参、一个出参。切数据类型相同
     */
    @Test
    public void BinaryOperatorTest() {
        BinaryOperator<Integer> binaryOperator = (t, y) -> {
            return t * y;
        };
        System.out.println(binaryOperator.apply(4, 6).toString());
    }

    /**
     * 两个参数的消费者
     */
    @Test
    public void biConsumerTest() {
        BiConsumer consumer = (x, y) -> {
            System.out.println("参数一:" + x.toString());
            System.out.println("参数er:" + y.toString());
        };
        consumer.accept(1, 2);
    }

    /**
     * 一元运算，入参出参同种类型
     */
    @Test
    public void UnaryOperatorTest() {
        UnaryOperator<String> unaryOperator = (t) -> {
            return "test";
        };
        System.out.println(unaryOperator.apply("test"));
    }

    /**
     * 二元运算，入参出参同种类型
     */
    @Test
    public void UnaryOperatorTest2() {
        BinaryOperator<String> binaryOperator = (t, c) -> {
            return t + c;
        };
    }
}