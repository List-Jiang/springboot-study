package com.jdw.sys.controller;


import com.jdw.sys.entity.User;
import com.jdw.sys.service.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/sys/user")
public class UserController {
    private final IUserService service;

    public void login(@RequestParam(value = "account") String account, @RequestParam(value = "password") String password) {
        User user = service.getUserByAccount(account);
        String str = DigestUtils.md5DigestAsHex((account + password).getBytes());
        if (user.getPassword().equals(str)) {
            user = service.getUserById(user.getId());
        }
    }

    @RequestMapping("/test")
    @Operation(summary = "用户测试")
    public void localDateTime(User user) {
        System.out.println(user.getCreateDate());
        System.out.println(user.getUpdateDate());
    }
}
