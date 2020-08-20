package com.jdw.sys.controller;

import cn.hutool.json.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.WebAsyncTask;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Instant;
import java.util.concurrent.*;

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

    //注入执行 threadPool 线程池
    @Resource(name = "threadPool")
    private ThreadPoolTaskExecutor treadPool;

    @RequestMapping("/test")
    public String test(){
        long startTime = Instant.now().toEpochMilli();
        try {
            //jdk8函数式编程，对于treadPool.submit而言，执行的代码块，有返回值的就是callable没有返回值的就是runnable。此处类似于java的重载
            //Callable案例1、2、3
            Future<String> submit1 = treadPool.submit(() -> {Thread.sleep(1000);return "第1个子线程任务";});
            Future<String> submit2 = treadPool.submit(() -> {Thread.sleep(2000);return "第1个子线程任务";});
            Future<String> submit3 = treadPool.submit(() -> {Thread.sleep(3000);return "第1个子线程任务";});
            //Runnable案例1
            Future<?> submit = treadPool.submit(() -> {
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            //callable产生的Future.get方法会等待子线程执行完返回值，
            String s1 = submit1.get();
            String s2 = submit2.get();
            String s3 = submit3.get();
            //注意，runnable没有返回值，get得到的值永远为null，但是get()方法依然是需要等待子线程执行完毕的。
            Object o = submit.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        long endTime = Instant.now().toEpochMilli();
        //总计执行时间为4000+ms。
        return "总计执行时间为"+(endTime-startTime)+"ms";
    }

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

    /**
     * 主线程开子线程异步执行多个任务，全部执行完毕后主线程结束。
     */
    @RequestMapping("/asyncSyncTask")
    public void asyncSyncTask(){
        long startTime = Instant.now().toEpochMilli();
        //jdk8函数式编程，对于treadPool.submit而言，执行的代码块，有返回值的就是callable没有返回值的就是runnable。此处类似于java的重载
        //线程程池提交一个callable。
        Future submit1 = treadPool.submit(()->{ Thread.sleep(1000); return "第一个任务睡了1秒"; });
        Future submit2 = treadPool.submit(()->{ Thread.sleep(2000); return "第一个任务睡了2秒"; });
        Future submit3 = treadPool.submit(()->{ Thread.sleep(3000); return "第一个任务睡了3秒"; });
        try {
            //获取结果，并设置获取结果动作的超时时间，超时会抛出TimeoutException异常
            System.out.println(submit3.get(2, TimeUnit.SECONDS).toString());
            System.out.println(submit1.get(2, TimeUnit.SECONDS).toString());
            System.out.println(submit2.get(2, TimeUnit.SECONDS).toString());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        long enDtime = Instant.now().toEpochMilli();
        System.out.println("所有任务总共执行时间为"+(enDtime-startTime)+"ms");
    }
}
