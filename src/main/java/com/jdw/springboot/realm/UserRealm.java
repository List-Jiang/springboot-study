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
import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
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

    /**
     *   修改凭证匹配器，
     *   该匹配器 校验 的时候会进行MD5加密并散射1024次后再进行 equal 比较
     *   加盐
     *   String password_md5 = (new Md5Hash("123","salt",1024)).toHex();
     *   未加盐
     *   String password_md5 = (new Md5Hash("123",1024)).toHex();
     */
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
     * 授权 通过认证证书主体获取权限,
     * 每次用户判断是否拥有某一个权限时，会先去缓存里面查询是否保存了这个用户的相关缓存。没有的话会调用这个方法再判断。
     * @param principals 凭证主体，例如，account、username、userId等
     * @return 权限
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String s = principals.getPrimaryPrincipal().toString();
        log.info("用户主体是："+s);
        //创建简单授权信息对象
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //添加角色
        info.addRole("super");
        info.addRole("admin");
        info.addRole("simple");
        //添加自定义 字符串权限       角色：操作：资源
        info.addStringPermission("super:*:01");
        info.addStringPermission("admin:*:01");
        return info;
    }

    /**
     * 认证  subject.login(token)会调用这里返回认证证书用户认证
     * @param token 登录token
     * @return 认证证书
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
