package com.jdw.springboot;

import com.baomidou.mybatisplus.core.toolkit.AES;
import com.jdw.SpringbootApplication;
import com.jdw.sys.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.test.web.servlet.MockMvc;

import javax.annotation.Resource;
import java.time.Instant;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

@Slf4j
@SpringBootTest(classes = SpringbootApplication.class)
@AutoConfigureMockMvc
class SpringbootApplicationTests {

    @Autowired
    IUserService userService;
    @Autowired
    MockMvc mockMvc;

    @Resource
    private ThreadPoolTaskExecutor taskExecutor;


    @Test
    void contextLoads() {
        String e4c402cc1617adfe = AES.decrypt("7REK79KuhjwO8SEYPYF8DcGjWGysUH1C606MMw0SNmGakcy2Ri9/MAZY/3UHgr4nAjRyDPBdexyiajCBnyDim+55l+iou2CjwMKcPt4l2spNaQ0joBoxVxKzLWUcZVzvECUTSgdrWGsG7quOa/K19YT/RpypcgHDMVnNCk04ork=", "e4c402cc1617adfe");
        String ttt = AES.encrypt("jdbc:mysql://localhost:3306/test2?serverTimezone=GMT%2b8&zeroDateTimeBehavior=convertToNull&characterEncoding=utf8", "e4c402cc1617adfe");
    }

    @Test
    public void test3() {
        Set<String> singleton1 = Collections.singleton("*");
        Set<String> singleton2 = Collections.singleton("get");
        System.out.println(singleton1.containsAll(singleton2));
    }

    @Test
    public void CallableTest() throws ExecutionException, InterruptedException {
        Callable<String> callable = () -> {
            Thread.sleep(1000);
            System.out.println(Instant.now().toEpochMilli() + "==>" + Thread.currentThread().getName());
            return "任务执行完毕";
        };
        String s = taskExecutor.submit(callable).get();
        System.out.println(s);
    }

    @Test
    void formatterTest() {

    }
}