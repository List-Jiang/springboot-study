package com.jdw.springboot;

import org.junit.Test;

import java.util.function.Function;

/**
 * @author ListJiang
 * @class jdk8函数式接口测试
 * @remark
 * @date 2020/12/16 19:24
 */

public class FunctionTest {

    @Test
    public void FunctionTestCode(){
        Function<String,String> function = (t)->{
            System.out.println("入参:"+t);
            return t.equals("test")?"true":"false";
        };
    }
}
