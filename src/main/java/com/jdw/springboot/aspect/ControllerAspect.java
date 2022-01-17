package com.jdw.springboot.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * @author ListJiang
 * @class controller切面处理
 * @remark
 * @date 2020/6/810:22
 */
//标明这是一个切面类
@Aspect
//标明这个类由 spring 自动管理
@Component
//引用 lombok 的 log
@Slf4j
public class ControllerAspect {
    //设置切面
    // 扫描类
    // @Pointcut(value = "within(com.jdw.sys.controller.*)")
    // 扫描注解
    // @Pointcut(value = "@annotation(org.springframework.web.bind.annotation.GetMapping)")
    // 扫描 com.jdw.sys.controller 下的类及其下面子包内的类 的 访问修饰符为 public 方法(public可以不设置,则扫描所有)
    // 注意事项：面向切面编程，最小的交互面是类，而不是方法。方法是在类的面上的一块。
    @Pointcut(value = " execution(public * com.jdw.sys.controller..*.*(..))")
    public void webLog() {
    }

    //方法执行前执行
    @Before("webLog()")
    public void doBefor(JoinPoint joinPoint) {
        //接受请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        //打印请求内容
        log.info("URL：" + request.getRequestURI());
        log.info("HTTP_METHOD：" + request.getMethod());
//        log.info("IP："+request.getRemoteAddr());
//        log.info("HOST："+request.getRemoteHost());
//        log.info("PORT："+request.getRemotePort());
        Enumeration<String> parameterNames = request.getParameterNames();
        log.info("请求参数列表为：");
        while (parameterNames.hasMoreElements()) {
            String s = parameterNames.nextElement();
            log.info(s + "：" + request.getParameter(s));
        }
    }

    /**
     * 退出执行,final增强，不管是抛出异常或者正常退出都会执行
     */
    @After("webLog()")
    public void doAfter() {
        log.info("After");
    }

    /**
     * 方法正常退出时执行,在 @After 后面执行
     */
    @AfterReturning(returning = "object", pointcut = "webLog()")
    public void doAfterReturning(Object object) {
        log.info("RESPONSE：" + object);
    }

    /**
     * 抛出异常执行,在 @After 后面执行
     */
    @AfterThrowing(value = "webLog()", throwing = "exception")
    public void doAfterThrowing(JoinPoint joinPoint, Exception exception) {
        log.info(joinPoint.getSignature().getName() + "抛出异常:" + exception.getMessage());
    }

    /**
     * 环绕通知：
     * 环绕通知非常强大，可以决定目标方法是否执行，什么时候执行，执行时是否需要替换方法参数，执行完毕是否需要替换返回值。
     * 环绕通知第一个参数必须是org.aspectj.lang.ProceedingJoinPoint类型
     * 如过环绕通知决定不执行目标方法,则其他切面注解定义的处理都不会执行.
     */
    @Around("webLog()")
    public Object aroundAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        log.info("- - - - - 环绕通知 - - - -");
        log.info("环绕通知的目标方法名：" + proceedingJoinPoint.getSignature().getName());
        Object obj = proceedingJoinPoint.proceed();//调用执行目标方法
        log.info("- - - - - 环绕通知 end - - - -");
        return obj;//返回目标方法返回值
    }

}
