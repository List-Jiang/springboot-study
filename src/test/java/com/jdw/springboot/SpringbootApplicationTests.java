package com.jdw.springboot;

import com.baomidou.mybatisplus.core.toolkit.AES;
import com.jdw.springboot.realm.UserRealm;
import com.jdw.sys.mapper.UserMapper;
import com.jdw.sys.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authc.credential.Md5CredentialsMatcher;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.DigestUtils;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SpringbootApplication.class)
@Slf4j
class SpringbootApplicationTests {
    @Autowired
    UserRealm userRealm;
    @Autowired
    IUserService userService;
    @Test
    void contextLoads() {
        String e4c402cc1617adfe = AES.decrypt("7REK79KuhjwO8SEYPYF8DcGjWGysUH1C606MMw0SNmGakcy2Ri9/MAZY/3UHgr4nAjRyDPBdexyiajCBnyDim+55l+iou2CjwMKcPt4l2spNaQ0joBoxVxKzLWUcZVzvECUTSgdrWGsG7quOa/K19YT/RpypcgHDMVnNCk04ork=", "e4c402cc1617adfe");
        String ttt = AES.encrypt("jdbc:mysql://localhost:3306/test2?serverTimezone=GMT%2b8&zeroDateTimeBehavior=convertToNull&characterEncoding=utf8", "e4c402cc1617adfe");
    }

    @Test
    public void test1(){
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
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("md5");

        //创建 token
        //密码MD5加盐（用户名）
        String password_md5 = (new Md5Hash("123", "123",1024)).toHex();
        UsernamePasswordToken token = new UsernamePasswordToken("Zhangsan","123");
        try {
            subject.login(token);
            log.info("认证成功");
        } catch (UnknownAccountException e) {
            log.error("认证失败：用户~"+token.getUsername()+"不存在！");
        } catch (IncorrectCredentialsException e){
            log.error("认证失败：用户密码不匹配！");
        }
    }

    @Test
    public void test3(){
        for (int i = 0; i < 3; i++) {
            userService.getUserByAccount("dsfdsfd");
            List<String> strings = userService.AllAccount();
        }
    }
}
