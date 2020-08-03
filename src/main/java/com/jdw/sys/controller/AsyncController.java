package com.jdw.sys.controller;

import cn.hutool.json.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.WebAsyncTask;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeoutException;

/**
 * @author ListJiang
 * @class 请求异步处理controller
 * @remark 主要用于解决一个接口单位时间内的高并发请求
 * @date 2020/8/310:45
 */
@RestController
@RequestMapping("/async")
@Slf4j
public class AsyncController {

    @Resource(name = "threadPool")
    private ThreadPoolTaskExecutor treadPool;

    /**
     * controller层面异步线程处理controller，当主线程跑到controller层面的时候，后续任务交给线程池处理，主线程销毁。
     * @param request
     * @param response
     * @param sysCode
     * @param apiCode
     * @param jsonObject
     * @return
     * @throws Exception
     */
    @Async("threadPool")
    @RequestMapping("/test1")
    public ListenableFuture<String> genBillCode(HttpServletRequest request, HttpServletResponse response
            , @PathVariable("sysCode") String sysCode
            , @PathVariable("apiCode") String apiCode
            , @RequestBody JSONObject jsonObject
    ){
        /**
         * 异步线程处理任务代码块。此段多线程并发，其实springboot本身此段就是主线程池并发的，此处是为了
         * 使用自定义线程池
         */
        {
            StringBuffer billCodeStr = new StringBuffer();
            System.out.println(Thread.currentThread().getName());
        }
        return new AsyncResult<>(Thread.currentThread().getName() + jsonObject);
    }
    /**
     * 使用 Callable 实现接口并发请求处理
     * 暂时无法使用你自定义线程池管理，默认采用 mvc
     * @return
     */
    @PostMapping("/callable")
    public Callable<String> helloController(@RequestBody JSONObject jsonObject) {
        log.info(Thread.currentThread().getName() + " 进入helloController方法");
        Integer i = jsonObject.getInt("i");
        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() throws Exception {
                log.info(Thread.currentThread().getName() + " 进入call方法");
                Thread.sleep(5000);
                log.info(Thread.currentThread().getName() + " 从helloService方法返回");
                return "从helloService方法返回"+Thread.currentThread().getName()+"say";
            }
        };
        log.info(Thread.currentThread().getName() + " 从helloController方法返回");
        return callable;
    }

    /**
     * 使用 WebAsyncTask 实现接口并发请求处理
     * 优点是自带异常，超时，结束回调设置，且可以使用自定义线程池处理
     * @return
     */
    @PostMapping("/webasynctask")
    public WebAsyncTask<String> exceptionController() {
        log.info(Thread.currentThread().getName() + " 进入helloController方法");
        Callable<String> callable = new Callable<String>() {
            @Override
            //线程正常执行返回结果给前端
            public String call() throws Exception {
                log.info(Thread.currentThread().getName() + " 进入call方法");
                /*Thread.sleep(5000);
                return Thread.currentThread().getName()+"子线程执行完毕";*/
                throw new TimeoutException("调用超时!");
            }
        };
        log.info(Thread.currentThread().getName() + " 从helloController方法返回");
        //指定超时时间，线程池，执行对象。
        WebAsyncTask<String> webAsyncTask = new WebAsyncTask<>(2000l,treadPool,callable);
        //线程结束回调，不返回结果。
        webAsyncTask.onCompletion(new Runnable() {
            @Override
            public void run() {
                log.info(Thread.currentThread().getName()+"线程结束回调");
            }
        });
        //错误回调，返回结果给前端
        webAsyncTask.onError(new Callable<String>() {
            @Override
            public String call() throws Exception {
                log.info(Thread.currentThread().getName()+"线程发生错误回调");
                return "线程发生错误回调";
            }
        });
        //超时回调，返回结果给前端
        webAsyncTask.onTimeout(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return Thread.currentThread().getName()+"线程执行超时了";
            }
        });
        return webAsyncTask;
    }
}
