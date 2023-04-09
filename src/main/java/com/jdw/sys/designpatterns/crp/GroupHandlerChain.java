package com.jdw.sys.designpatterns.crp;

import com.alibaba.fastjson2.JSONObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class GroupHandlerChain {
    private final List<GroupHandler> groupHandlers;

    public void handle(JSONObject jsonObject) {
        for (GroupHandler groupHandler : groupHandlers) {
            if (!groupHandler.handleAndContinue(jsonObject)) {
                String interruptMessage = groupHandler.getInterruptMessage();
                log.info("分组器中断消息为：{}", interruptMessage);
                break;
            }
        }
    }
}
