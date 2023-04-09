package com.jdw.sys.designpatterns.crp;

import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Slf4j
@Service
public class SexHandler implements GroupHandler {

    @Override
    public boolean handleAndContinue(JSONObject jsonObject) {
        log.info("性别校验开始");
        String sex = jsonObject.getString("sex");
        boolean continueState = Arrays.asList("男", "女").contains(sex);
        log.info("性别为：{}", sex);
        if (continueState) {
            jsonObject.put("sexMessage", "性别校验通过");
        } else {
            jsonObject.put("ageMessage", "性别校验不通过");
        }
        return continueState;
    }


    @Override
    public int getOrder() {
        return 0;
    }


    @Override
    public String getInterruptMessage() {
        return "性别不合法";
    }
}
