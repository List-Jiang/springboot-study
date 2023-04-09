package com.jdw.sys.designpatterns.crp;

import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AgeHandler implements GroupHandler {

    @Override
    public boolean handleAndContinue(JSONObject jsonObject) {
        log.info("年龄校验开始");
        int age = jsonObject.getIntValue("age");
        log.info("年龄为：{}", age);
        boolean continueState = 0 <= age && age < 200;
        if (continueState) {
            jsonObject.put("ageMessage", "年龄校验通过");
        } else {
            jsonObject.put("ageMessage", "年龄校验不通过");
        }
        return continueState;
    }

    @Override
    public String getInterruptMessage() {
        return "年龄不合法";
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
