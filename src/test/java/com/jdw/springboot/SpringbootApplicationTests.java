package com.jdw.springboot;

import com.baomidou.mybatisplus.core.toolkit.AES;
import com.jdw.sys.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SpringbootApplication.class)
@Slf4j
class SpringbootApplicationTests {
    @Autowired
    IUserService userService;
    @Resource
    private RestTemplate restTemplate;

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
    public void password() {
        for (int i = 0; i < 500; i++) {

        }
    }
}
