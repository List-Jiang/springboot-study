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
    public void test1(){
        List<String> strings = userService.AllAccount();
        strings.forEach(t->{
            System.out.println(t);
        });
        //1、创建安全管理器对象
        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        //2、给安全管理器设置 realm
        Realm realm = null;
        securityManager.setRealm(new IniRealm("classpath:config/shiro.ini"));
        //3、SecurityUtils 全局安全管理工具类设置安全管理器
        SecurityUtils.setSecurityManager(securityManager);
        //4、获取关键对象 subject 主体
        Subject subject = SecurityUtils.getSubject();
        //5、创建令牌
        UsernamePasswordToken token = new UsernamePasswordToken("zhangsan","123");
        try {
            /**
             * 用户校验，分为两步，
             * 第一步：核心为调用 SimpleAccountRealm.doGetAuthenticationInfo
             *     校验用户名，不存在抛出 UnknownAccountException
             * 第一步：核心为调用 AuthenticatingRealm.assertCredentialsMatch
             *    校验通过用户名获取的 info 与token是否匹配，不匹配抛出 IncorrectCredentialsException
             */
            subject.login(token);
            System.out.println(subject.isAuthenticated());
        } catch (UnknownAccountException e) {
            System.out.println("认证失败：用户~"+token.getUsername()+"不存在！");
            e.printStackTrace();
        } catch (IncorrectCredentialsException e){
            System.out.println("认证失败：用户密码不匹配！");
            e.printStackTrace();
        }
    }

    /**
     * shiro权限管理认证、授权相关测试
     */
    @Test
    public void test2(){
        //创建默认安全管理器 securityManager
        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        //安全管理器 设置自定义 realm
        securityManager.setRealm(userRealm);
        //安全工具类设置安全管理器 securityManager
        SecurityUtils.setSecurityManager(securityManager);
        //安全工具类获取主体 subject
        Subject subject = SecurityUtils.getSubject();
        //创建 token
        UsernamePasswordToken token = new UsernamePasswordToken("Zhangsan","123");
        try {
            //认证token
            subject.login(token);
            log.info("认证成功");
        } catch (UnknownAccountException e) {
            log.error("认证失败：用户~"+token.getUsername()+"不存在！");
        } catch (IncorrectCredentialsException e){
            log.error("认证失败：用户密码不匹配！");
        }
        //判断用户已经通过身份认证
        if (subject.isAuthenticated()){
            log.info("用户已经通过身份认证");
            //判断用户是否有角色 super
            if(subject.hasRole("super")){
                log.info(subject.getPrincipals().toString()+"拥有 super 角色");
            }
            //判断用户是否有同时拥有 super,admin,simple 角色
            if(subject.hasAllRoles(Arrays.asList("super","admin","simple"))){
                log.info(subject.getPrincipal().toString()+"同时拥有 super,admin,simple 角色");
            }
            //获取用户对于角色 super,admin,simple 拥有状态分别是
            boolean[] booleans = subject.hasRoles(Arrays.asList("super", "admin", "simple"));
                log.info(subject.getPrincipal().toString()+"对于角色 super,admin,simple 拥有状态分别是");
            for (boolean aBoolean : booleans) {
                log.info(Boolean.toString(aBoolean));
            }
            //判断用户是否拥有 super:*:01 权限
            if(subject.isPermitted("super:get:02")){
                log.info(subject.getPrincipal().toString()+"用户拥有 super:*:01 权限");
            }else{
                log.error(subject.getPrincipal().toString()+"用户未拥有 super:*:01 权限");
            }

        }else{
            log.error("用户未通过身份认证");
        }
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
