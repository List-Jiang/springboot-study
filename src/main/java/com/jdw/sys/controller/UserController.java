package com.jdw.sys.controller;


import com.jdw.springboot.enums.SexEnum;
import com.jdw.springboot.enums.StatusEnum;
import com.jdw.sys.entity.User;
import com.jdw.sys.service.IUserService;
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
public class UserController {
    @Autowired
    IUserService service;


    @GetMapping("/get")
    @ResponseBody
    public User getUser(@RequestParam("id")String id){
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
