package com.jdw.sys.controller;

import com.jdw.springboot.config.CustomVariableConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;

/**
 * 简易前端处理器
 * get不可涉及数据的请求，例如，登录，忘记密码，网站介绍......
 *
 * @author 蒋德文
 * @since 2020/7/2513:01
 */
@Controller
public class SimpleViewController {
    //value注解案例
    @Value("#{${custom.map}}")
    private Map<String, String> map;
    @Value("#{'${custom.list1}'.split(',')}")
    private List<String> list;
    //引用使用 ConfigurationProperties注解 的参数配置类
    @Autowired
    private CustomVariableConfig customVariableConfig;

    @GetMapping("/user/{view}")
    public String Simple(@PathVariable(name = "view") String view) {
        String templatePaath = "500.ftl";
        for (int i = 0; i < customVariableConfig.getSimpleViewControllers().size(); i++) {
            String urlParame = customVariableConfig.getSimpleViewControllers().get(i).get("urlParame");
            templatePaath = customVariableConfig.getSimpleViewControllers().get(i).get("templatePath");
            if (urlParame.equals(view)) {
                return templatePaath;
            }
        }
        return templatePaath;
    }
}
