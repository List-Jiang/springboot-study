package com.jdw.springboot;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.AES;
import com.jdw.springboot.realm.UserRealm;
import com.jdw.sys.entity.User;
import com.jdw.sys.mapper.UserMapper;
import com.jdw.sys.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authc.credential.Md5CredentialsMatcher;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.realm.RealmFactory;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.DigestUtils;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SpringbootApplication.class)
@Slf4j
class SpringbootApplicationTests {
    @Autowired
    UserRealm userRealm;
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
    public void test3(){
        Set<String> singleton1 = Collections.singleton("*");
        Set<String> singleton2 = Collections.singleton("get");
        System.out.println(singleton1.containsAll(singleton2));
    }

    @Test
    public void password(){
        for (int i =0 ;i<500;i++){
            
        }
    }
}
