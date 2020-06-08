package com.jdw.springboot.config;

import com.jdw.springboot.realm.UserRealm;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.session.mgt.eis.MemorySessionDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author ListJiang
 * @class Shiro配置类
 * @remark
 * @date 2020/5/3010:49
 */
//@Configuration
public class ShiroConfig {


    @Bean
    public EhCacheManager getEhCachemanager(){
        EhCacheManager ehcachemanager = new EhCacheManager();
        ehcachemanager.setCacheManagerConfigFile("classpath:config/ehcache-shiro.xml");
        return ehcachemanager;
    }

    @Bean
    public UserRealm getUserRealm(){
        UserRealm userRealm = new UserRealm();
        userRealm.setCacheManager(getEhCachemanager());
        return userRealm;
    }

    @Bean(name = "securityManager")
    public DefaultSecurityManager getDefaultSecurityManager(UserRealm userRealm){
        DefaultSecurityManager manager = new DefaultSecurityManager();
        manager.setRealm(userRealm);
        /*用户授权/认证信息 Cache,采用EhCache 缓存*/
        manager.setCacheManager(getEhCachemanager());
        manager.setSessionManager(getDefaultWebSessionManager());
        return manager;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //拦截器
        Map<String,String>  filterChainDefinitionMap  = new LinkedHashMap<>();
        //配置允许访问的静态资源
        filterChainDefinitionMap.put("/js/**","anon");
        filterChainDefinitionMap.put("/css/**","anon");
        filterChainDefinitionMap.put("/img/**","anon");
        //配置拦截所有url必须认证  authc
        filterChainDefinitionMap.put("/**","authc");
        //设置登录 login页面链接
        shiroFilterFactoryBean.setLoginUrl("/login");
        //设置未授权失败界面
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");

        Map<String, Filter> filterMap = new HashMap<>();
        shiroFilterFactoryBean.setFilters(filterMap);
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    //开启 Controller 中的 Shiro 注解
    @Bean
    public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator de = new DefaultAdvisorAutoProxyCreator();
        de.setProxyTargetClass(true);
        return de;
    }

    @Bean
    public JavaUuidSessionIdGenerator getJavaUuidSessionIdGenerator(){
        return new JavaUuidSessionIdGenerator();
    }

    @Bean
    public MemorySessionDAO getMemorySessionDAO(){
        MemorySessionDAO dao = new MemorySessionDAO();
        dao.setSessionIdGenerator(getJavaUuidSessionIdGenerator());
        return dao;
    }

    /**
     * 设置 Shiro 自定义 cookie
     * @return
     */
    @Bean
    public SimpleCookie getSimpleCookie(){
        SimpleCookie cookie = new SimpleCookie();
        cookie.setName("security.session.id");
        cookie.setPath("/");
        return cookie;
    }

    @Bean
    public LifecycleBeanPostProcessor getLifecycleBeanPostProcessor(){
        return new LifecycleBeanPostProcessor();
    }

    //配置 Shiro Session 管理器
    @Bean
    public DefaultWebSessionManager getDefaultWebSessionManager(){
        DefaultWebSessionManager manager = new DefaultWebSessionManager();
        manager.setSessionDAO(getMemorySessionDAO());
        manager.setGlobalSessionTimeout(3600000);
        manager.setSessionIdCookieEnabled(true);
        manager.setSessionIdCookie(getSimpleCookie());
        manager.setSessionValidationSchedulerEnabled(true);
        return manager;
    }

    /**
     * 开启 Shiro 注解支持
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor getAuthorizationAttributeSourceAdvisor(UserRealm realm){
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(getDefaultSecurityManager(realm));
        return advisor;
    }


}
