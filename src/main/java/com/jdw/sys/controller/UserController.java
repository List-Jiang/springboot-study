package com.jdw.sys.controller;


import com.jdw.sys.entity.User;
import com.jdw.sys.service.IUserService;
import com.jdw.sys.vo.Result;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestBody;
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
    private final IUserService iUserService;

    public void login(@RequestParam(value = "account") String account, @RequestParam(value = "password") String password) {
        User user = iUserService.getUserByAccount(account);
        String str = DigestUtils.md5DigestAsHex((account + password).getBytes());
        if (user.getPassword().equals(str)) {
            user = iUserService.getUserById(user.getId());
        }
    }

    @RequestMapping("/test")
    @Operation(summary = "用户测试")
    public Result<User> localDateTime(@RequestBody User user) {
        return iUserService.saveOrUpdate(user) ? Result.ok(user) : Result.error(null, user);
    }
}
