package com.jdw.springboot.formatter;

import com.alibaba.fastjson2.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.net.URI;

/**
 * description Spring 数据序列化相关测试
 *
 * @author ListJiang
 * @since 2022-01-19
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class FormatterTest {

    @Test
    void dateTest(@Autowired MockMvc mockMvc) throws Exception {
        JSONObject jsonObject = JSONObject.parseObject("{\"localDate\":\"2022-01-19\",\"localDateTime\":\"2022-01-19 " +
                "09:37:16\"," +
                "\"localTime\":\"09:37:16\",\"date\":\"2022-01-19 09:37:16\",\"calendar\":\"2022-01-19 09:37:16\"," +
                "\"bigDecimal1\":0.0,\"bigDecimal2\":0.0,\"money\":\"￥ 12312.12\",\"province\":\"阿飞省\"," +
                "\"city\":\"飞洒发市\",\"county\":\"同一天区\"}");

        mockMvc.perform(MockMvcRequestBuilders.post(URI.create("/formatter/post"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonObject.toJSONString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(result -> {
                    JSONObject jsonObject1 = JSONObject.parseObject(result.getRequest().getContentAsString());
                    assert jsonObject1 != null;
                    Assertions.assertTrue(jsonObject1.getString("money").startsWith("￥"), "money 未序列化为以 ￥ 开头的字符串");
                });
    }
}