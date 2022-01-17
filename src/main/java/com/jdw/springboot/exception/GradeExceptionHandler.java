package com.jdw.springboot.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author ListJiang
 * @class description  全局捕获异常类、只能捕获被代码抛出未处理的异常
 * @remark 如果异常被try{}catch(){}处理了，自然无法进入这里
 * @date 2020/5/2 15:45
 */
@Slf4j
@RestControllerAdvice
public class GradeExceptionHandler {

    // 处理接口参数数据格式错误异常
    // 使用 MethodArgumentNotValidException.class 无法处理通过 @ModelAttribute 绑定的 GET 请求
    @ExceptionHandler(value = BindException.class)
    public Object errorHandler(HttpServletRequest request, BindException e) {
        // 详情
        List<ObjectError> allErrors = e.getBindingResult().getAllErrors();
        // 移除敏感信息
        List<Map<String, Object>> collect = allErrors.stream().map(t -> {
            Map<String, Object> map = new HashMap<>(2);
            map.put("defaultMessage", t.getDefaultMessage());
            switch (t) {
                case FieldError fieldError -> {
                    map.put("rejectedValue", fieldError.getRejectedValue());
                    map.put("defaultMessage", t.getDefaultMessage());
                }
                default -> {
                }
            }
            return map;
        }).collect(Collectors.toList());

        return collect;
    }

    @ExceptionHandler(HttpMessageConversionException.class)
    public String httpMessageConversionException(HttpMessageConversionException exception) {
        //控制台输出异常
        String message = getMessage(exception);
        log.error(message);
        return "非法的请求体";
    }

    @ExceptionHandler(Exception.class)
    public String runTimeException(Exception exception) {
        //控制台输出异常
        String message = getMessage(exception);
        log.error(message);
        return "系统出错，请联系管理员";
    }

    public static String getMessage(Throwable t) {
        StringWriter stringWriter = new StringWriter();
        t.printStackTrace(new PrintWriter(stringWriter, true));
        return stringWriter.getBuffer().toString();
    }
}
