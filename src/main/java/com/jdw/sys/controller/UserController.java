package com.jdw.sys.controller;


import com.jdw.springboot.enums.SexEnum;
import com.jdw.springboot.enums.StatusEnum;
import com.jdw.springboot.realm.UserRealm;
import com.jdw.sys.entity.User;
import com.jdw.sys.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jdw
 * @since 2020-05-27
 */
@Controller
@RequestMapping("/sys/user")
@Slf4j
public class UserController {
    @Autowired
    IUserService service;
    @Autowired
    UserRealm userRealm;


    @GetMapping("/get")
    @ResponseBody
    public User getUser(@RequestParam("id")String id){
        //创建自定义 securityManager
        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        //安全管理器 设置自定义 realm
        securityManager.setRealm(userRealm);
        //安全工具类设置安全管理器 securityManager
        SecurityUtils.setSecurityManager(securityManager);
        //安全工具类获取主体 subject
        Subject subject = SecurityUtils.getSubject();
        //创建 token
        UsernamePasswordToken token = new UsernamePasswordToken("Jack","123");
        try {
            subject.login(token);
        } catch (UnknownAccountException e) {
            log.error("认证失败：用户~"+token.getUsername()+"不存在！");
            e.printStackTrace();
        } catch (IncorrectCredentialsException e){
            log.error("认证失败：用户密码不匹配！");
            e.printStackTrace();
        }
        User byId1 = service.getById("6");
        String str = null;
//        int i = str.indexOf(33);
        byId1 = service.getUserById(byId1.getId());
        return byId1;
    }

    public void login(@RequestParam(value = "account",required = true)String account,@RequestParam(value = "password",required = true)String password){
        User user = service.getUserByAccount(account);
        String str = DigestUtils.md5DigestAsHex((account + password).getBytes());
        if(user.getPassword().equals(str)){
            user = service.getUserById(user.getId());
        }
    }
}
