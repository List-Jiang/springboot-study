package com.jdw.sys.controller;


import com.baomidou.mybatisplus.core.toolkit.AES;
import com.jdw.sys.entity.Demo;
import com.jdw.sys.service.IDemoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


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

    @GetMapping("/delete")
    public String delete(@RequestParam("id") String id) {
        Demo byId = service.getById(id);
        boolean b = service.removeById(id);
        Demo byI2 = service.getById(id);
        //e4c402cc1617adfe
        String randomKey = AES.generateRandomKey();

        // 随机密钥加密
        String driver = AES.encrypt("com.mysql.cj.jdbc.Driver", randomKey);
        String url = AES.encrypt("jdbc:mysql://localhost:3306/test1?serverTimezone=GMT%2b8&zeroDateTimeBehavior=convertToNull&characterEncoding=utf8", randomKey);
        String user = AES.encrypt("test1", randomKey);
        String password = AES.encrypt("12345678", randomKey);
        String e4c402cc1617adfe = AES.decrypt("b6iFhx7TS4F7IYG3DeVpOQ==", "e4c402cc1617adfe");


        return b ? "1" : "0";
    }

}