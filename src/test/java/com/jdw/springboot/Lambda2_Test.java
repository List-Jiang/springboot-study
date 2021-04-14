package com.jdw.springboot;

import java.util.function.Consumer;

public class Lambda2_Test {

    private void test(Consumer<String> consumer) {
        consumer.accept("数据");
    }

    public static void main(String[] args) {
        Lambda2_Test lambda2_test = new Lambda2_Test();
        Consumer<String> consumerA = t -> System.out.println("A消费" + t);
        Consumer<String> consumerB = t -> System.out.println("B消费" + t);

        lambda2_test.test(consumerA);
        lambda2_test.test(consumerB);
    }

}
