package com.jdw.sys.designpatterns.crp;

import com.alibaba.fastjson2.JSONObject;
import org.springframework.core.Ordered;

public interface GroupHandler extends Ordered {
    boolean handleAndContinue(JSONObject jsonObject);

    String getInterruptMessage();

}
