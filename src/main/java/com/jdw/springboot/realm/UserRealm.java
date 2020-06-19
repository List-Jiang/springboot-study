package com.jdw.springboot.realm;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.jdw.sys.entity.User;
import com.jdw.sys.mapper.UserMapper;
import com.jdw.sys.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ListJiang
 * @class 用户登录拦截
 * @remark
 * @date 2020/5/3010:50
 */
@Slf4j
@Component
public class UserRealm extends AuthorizingRealm {
    @Resource
    IUserService service;
    @Resource
    UserMapper mapper;
    //非静态代码块，在创建对象的时候运行（即new一个对象的时候），每创建一次对象就执行一次
    {
        //手动设置 realm 的凭证匹配器
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        //凭证匹配器算法为 MD5
        credentialsMatcher.setHashAlgorithmName("md5");
        //凭证匹配器散列次数为 1024。不设置的话默认为 1。
        credentialsMatcher.setHashIterations(1024);
        setCredentialsMatcher(credentialsMatcher);
    }
    /**
     * 授权
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }

    /**
     * 认证
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        log.info(token.getPrincipal().toString());
        log.info(token.getCredentials().toString());
        List<User> userByName = mapper.getUserByName(token.getPrincipal().toString());
        User userByAccount = service.getUserByAccount(token.getPrincipal().toString());
        if(!ObjectUtils.isEmpty(userByAccount)){
            //通过 登录账号、登录密码新建简单证书返回
            SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(token.getPrincipal(), userByAccount.getPassword(), getName());
            //证书加盐
            simpleAuthenticationInfo.setCredentialsSalt(ByteSource.Util.bytes("123"));
            return simpleAuthenticationInfo;
        }
        return null;
    }


}
