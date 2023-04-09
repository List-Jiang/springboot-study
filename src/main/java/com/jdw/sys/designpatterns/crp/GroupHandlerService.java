package com.jdw.sys.designpatterns.crp;

import com.alibaba.fastjson2.JSONObject;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GroupHandlerService {
    private final GroupHandlerChain groupHandlerChain;

    public void handle(JSONObject jsonObject) {
        groupHandlerChain.handle(jsonObject);
    }
}
