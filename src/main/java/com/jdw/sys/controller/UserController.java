package com.jdw.sys.controller;


import com.jdw.sys.entity.User;
import com.jdw.sys.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * <p>
 * 前端控制器
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

    public void login(@RequestParam(value = "account", required = true) String account, @RequestParam(value = "password", required = true) String password) {
        User user = service.getUserByAccount(account);
        String str = DigestUtils.md5DigestAsHex((account + password).getBytes());
        if (user.getPassword().equals(str)) {
            user = service.getUserById(user.getId());
        }
    }

    @RequestMapping("/test")
    public void localDateTime(User user) {
        System.out.println(user.getCreateDate());
        System.out.println(user.getUpdateDate());
    }
}
