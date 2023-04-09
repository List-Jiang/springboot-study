package com.jdw.sys.controller;


import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.AES;
import com.jdw.sys.designpatterns.crp.GroupHandlerService;
import com.jdw.sys.entity.Demo;
import com.jdw.sys.service.IDemoService;
import com.jdw.sys.vo.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author jdw
 * @since 2020-05-26
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/sys/demo")
public class DemoController {
    private final IDemoService service;
    private final GroupHandlerService handlerService;

    @GetMapping("/delete")
    public String delete(@RequestParam("id") String id) {
        Demo byId = service.getById(id);
        boolean b = service.removeById(id);
        Demo byI2 = service.getById(id);
        //e4c402cc1617adfe
        String randomKey = AES.generateRandomKey();

        // 随机密钥加密
        System.out.println("driver: " + AES.encrypt("com.mysql.cj.jdbc.Driver", randomKey));
        System.out.println("url: " + AES.encrypt("jdbc:mysql://localhost:3306/test1?serverTimezone=GMT%2b8&zeroDateTimeBehavior=convertToNull&characterEncoding=utf8", randomKey));
        System.out.println("user: " + AES.encrypt("test1", randomKey));
        System.out.println("password: " + AES.encrypt("12345678", randomKey));
        System.out.println("e4c402cc1617adfe: " + AES.decrypt("b6iFhx7TS4F7IYG3DeVpOQ==", "e4c402cc1617adfe"));


        return b ? "1" : "0";
    }

    @PostMapping("/handel")
    public Result<JSONObject> handler(@RequestBody JSONObject jsonObject) {
        handlerService.handle(jsonObject);
        return Result.ok(jsonObject);
    }

}