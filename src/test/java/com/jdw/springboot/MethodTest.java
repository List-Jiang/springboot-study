package com.jdw.springboot;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.function.Consumer;
import java.util.function.Supplier;

class Cat {

    public static void bark(Supplier<Cat> supplier) {
        System.out.println(supplier.get() + "发现了老鼠！");
    }

    @Override
    public String toString() {
        String name = "Tom";
        return name;
    }
}


/**
 * @author 蒋德文
 * @class 方法引用测试
 * @since 2021/1/15 13:10
 */
@SpringBootTest
public class MethodTest {

    @Test
    public void test1() {
        Consumer<String> consumer = System.out::println;
        consumer.accept("基础方法引用打印测试");
    }

    @Test
    public void CatTest() {
        // 生产者、消费者测试
        Supplier<Cat> catSupplier = Cat::new;
        Consumer<Supplier<Cat>> consumer = Cat::bark;
        consumer.accept(catSupplier);
    }
}