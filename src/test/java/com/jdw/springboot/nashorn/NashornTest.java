package com.jdw.springboot.nashorn;

import org.junit.jupiter.api.Test;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.stream.IntStream;

public class NashornTest {
    @Test
    public void test() throws ScriptException {
        IntStream.range(1, 100)
                .parallel()
                .forEach(t -> {
                    ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
                    long id = Thread.currentThread().getId();
                    try {
                        engine.eval(
                                "var greeting='hello world" + id + "';" +
                                        "print(greeting);" +
                                        "greeting");
                    } catch (ScriptException e) {
                        throw new RuntimeException(e);
                    }
                });

    }
}
