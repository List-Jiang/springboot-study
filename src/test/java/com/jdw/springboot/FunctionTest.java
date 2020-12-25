package com.jdw.springboot;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.function.*;

/**
 * @author ListJiang
 * @class jdk8函数式接口测试
 * @remark
 * @date 2020/12/16 19:24
 */
@Slf4j
public class FunctionTest {

    /**
     * 入参、出参不同类型
     */
    @Test
    public void FunctionTestCode() {
        Function<String, String> function = (t) -> {
            log.info("入参:" + t);
            return t.equals("test") ? "true" : "false";
        };
        function.apply("end");
        function.andThen(function).apply("test");
    }

    @Test
    public void BinaryOperator(){
        BinaryOperator<Integer> binaryOperator = (t,y)->{
            return t*y;
        };
        log.info(binaryOperator.apply(4,6).toString());
    }

    /**
     * 一元运算，入参出参同种类型
     */
    @Test
    public void UnaryOperatorTest(){
        UnaryOperator<String> unaryOperator = (t)->{
            return "test";
        };
        log.info(unaryOperator.apply("test"));
    }

    @Test
    public void SupplierTest(){
        Supplier<String> getString = ()->{
            log.info("开始生产随机数字");
            return Double.toString(Math.ceil(Math.random()));
        };
        log.info(getString.get());
    }

    @Test
    public void ConsumerTest() {
        Consumer isEnd = (t) -> {
            log.info("开始消费" + t);
            log.info("开始消费" + t);
        };
        isEnd.accept("消费");
    }

    @Test
    public void biConsumerTest(){
        BiConsumer consumer = (x,y)->{
            System.out.println("参数一:"+x.toString());
            System.out.println("参数er:"+y.toString());
        };
        consumer.accept(1,2);
    }

    @Test
    public void Predicate() {
        Predicate<String> isHz = t -> {
            log.info("判断参数为：" + t);
            return t.equals("Hz");
        };
        log.info(Boolean.toString(isHz.test("Hz")));
    }
}
