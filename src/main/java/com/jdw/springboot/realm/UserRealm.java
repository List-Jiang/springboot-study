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
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
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
        List<User> userByName = mapper.getUserByName(token.getPrincipal().toString());        User userByAccount = service.getUserByAccount(token.getPrincipal().toString());
        if(!ObjectUtils.isEmpty(userByAccount)){
            SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(token.getPrincipal(), userByAccount.getPassword(), getName());
            return simpleAuthenticationInfo;
        }
        return null;
    }
}
