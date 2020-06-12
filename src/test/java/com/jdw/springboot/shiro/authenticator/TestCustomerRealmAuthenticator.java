package com.jdw.springboot.shiro.authenticator;

import com.jdw.springboot.realm.UserRealm;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;

/**
 * @author ListJiang
 * @class shiro——自定义realm测试类
 * @remark
 * @date 2020/6/1117:13
 */
@Slf4j
public class TestCustomerRealmAuthenticator {
    public static void main(String[] args) {
        //创建自定义 securityManager
        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        //安全管理器 设置自定义 realm
        securityManager.setRealm(new UserRealm());
        //安全工具类设置安全管理器 securityManager
        SecurityUtils.setSecurityManager(securityManager);
        //安全工具类获取主体 subject
        Subject subject = SecurityUtils.getSubject();
        //创建 token
        UsernamePasswordToken token = new UsernamePasswordToken("张三","123");
        try {
            subject.login(token);
        } catch (UnknownAccountException e) {
            log.error("认证失败：用户~"+token.getUsername()+"不存在！");
            e.printStackTrace();
        } catch (IncorrectCredentialsException e){
            log.error("认证失败：用户密码不匹配！");
            e.printStackTrace();
        }
    }
}
