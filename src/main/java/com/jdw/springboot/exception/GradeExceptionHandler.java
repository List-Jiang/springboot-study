package com.jdw.springboot.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 全局捕获异常类、只能捕获被代码抛出未处理的异常
 *
 * @author 蒋德文
 * @since 2020/5/2 15:45
 */
@Slf4j
@RestControllerAdvice
public class GradeExceptionHandler {

    // 处理接口参数数据格式错误异常
    // 使用 MethodArgumentNotValidException.class 无法处理通过 @ModelAttribute 绑定的 GET 请求
    @ExceptionHandler(value = BindException.class)
    public Object errorHandler(HttpServletRequest request, BindException e) {
        return e.getBindingResult().getAllErrors()
                .stream()
                .map(t -> switch (t) {
                    case FieldError fieldError ->
                            Map.of("rejectedValue", Objects.requireNonNull(fieldError.getRejectedValue())
                                    , "rejectedMessage", Objects.requireNonNull(t.getDefaultMessage()));
                    default -> Map.of("rejectedMessage", Objects.requireNonNull(t.getDefaultMessage()));
                })
                .collect(Collectors.toList());
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
