package com.jdw.springboot.exception;

import lombok.extern.slf4j.Slf4j;
import lombok.extern.slf4j.XSlf4j;
import org.slf4j.ILoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author ListJiang
 * @class description  全局捕获异常类$
 * @remark 捕获全局异常统一返回页面或者提示信息
 * @date 2020/5/2 15:45
 */
@ControllerAdvice
@Slf4j
public class GradeException {
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public String runTimeException(Exception exception){
        //控制台输出异常
        exception.printStackTrace();
        return exception.getMessage();
    }
}
