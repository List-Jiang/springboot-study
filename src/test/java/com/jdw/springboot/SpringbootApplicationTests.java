package com.jdw.springboot;

import com.baomidou.mybatisplus.core.toolkit.AES;
import com.jdw.sys.entity.User;
import com.jdw.sys.mapper.UserMapper;
import com.jdw.sys.service.IUserService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SpringbootApplication.class)
@MapperScan("com.jdw.springboot")
class SpringbootApplicationTests {

    @Autowired
    UserMapper userMapper;

    @Autowired
    IUserService service;

    @Test
    void contextLoads() {
        String e4c402cc1617adfe = AES.decrypt("7REK79KuhjwO8SEYPYF8DcGjWGysUH1C606MMw0SNmGakcy2Ri9/MAZY/3UHgr4nAjRyDPBdexyiajCBnyDim+55l+iou2CjwMKcPt4l2spNaQ0joBoxVxKzLWUcZVzvECUTSgdrWGsG7quOa/K19YT/RpypcgHDMVnNCk04ork=", "e4c402cc1617adfe");
        String ttt = AES.encrypt("jdbc:mysql://localhost:3306/test2?serverTimezone=GMT%2b8&zeroDateTimeBehavior=convertToNull&characterEncoding=utf8", "e4c402cc1617adfe");
        User userById = userMapper.selectById("4");
        User userById2 = userMapper.selectById("4");
        User byId = service.getUserById(4l);

        System.out.println(userById);
    }

}
