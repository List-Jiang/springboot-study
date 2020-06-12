package com.jdw.springboot.shiro.authenticator;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;

/**
 * @author ListJiang
 * @class shiro 认证器测试类
 * @remark
 * @date 2020/6/1111:30
 */
public class TestAuthenticator {
    public void test1(){
        //1、创建安全管理器对象
        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        //2、给安全管理器设置 realm
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
    public static void main(String[] args) {
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
}
