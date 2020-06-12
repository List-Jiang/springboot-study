package com.jdw.springboot;

//import com.jdw.springboot.entity.User;
//import com.jdw.springboot.mapper.UserMapper;

import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootApplication
//springboot 设置 component 扫描路径
@ComponentScan(basePackages = {"com.jdw.*.*","com.jdw.*.*.*"})
//springboot 设置 mapper 扫描路径
@MapperScan("com.jdw.*.mapper")
//springboot 开启定时任务
//@EnableScheduling
//springboot 开启异步
@EnableAsync
public class SpringbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootApplication.class, args);
        for (String t:
             args) {
            System.out.println(t);
        }
    }

    /**
     * @author ListJiang
     * @class description  测试类
     * @remark
     * @date 2020/5/1411:37
     */
    @RunWith(SpringRunner.class)
    @SpringBootTest
    public static class test implements Runnable{
        public  static int t = 1;
        @Override
        public void run() {
            for (int i = 0; i < 100; i++) {
                System.out.println("开始一个线程！");
                System.out.println(t++);
            }
        }

       /* @Autowired
        private UserMapper userMapper;

        @Test
        public void testSelect() {
            System.out.println(("----- selectAll method test ------"));
            List<User> userList = userMapper.selectList(null);
            Assert.assertEquals(5, userList.size());
            userList.forEach(System.out::println);
        }
        public static void main(String[] args) {
            test t  = new test();
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < 100; i++) {
                        System.out.println("这是一个线程"+t);
                    }
                }
            };
            new Thread(t).start();
            new Thread(runnable).start();
        }*/
    }
}
