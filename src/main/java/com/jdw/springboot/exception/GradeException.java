package com.jdw.springboot.exception;

import lombok.extern.slf4j.Slf4j;
import lombok.extern.slf4j.XSlf4j;
import org.slf4j.ILoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author ListJiang
 * @class description  全局捕获异常类、只能捕获被代码抛出未处理的异常
 * @remark 如果异常被try{}catch(){}处理了，自然无法进入这里
 * @date 2020/5/2 15:45
 */
@Slf4j
//@ControllerAdvice
@RestControllerAdvice
public class GradeException {
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public String runTimeException(Exception exception){
        //控制台输出异常
        String message = getMessage(exception);
        log.error(message);
        return message;
    }

    public static String getMessage(Throwable t){
        StringWriter stringWriter=new StringWriter();
        t.printStackTrace(new PrintWriter(stringWriter,true));
        return stringWriter.getBuffer().toString();
    }
}
