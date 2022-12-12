package com.jdw.springboot.generator;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * mybatis-plus代码生成器
 *
 * @author 蒋德文
 * @since 2021/7/1 14:30
 */
public class CodeGenerator {

    private static final String projectPath = "D:\\temp";
    private static final String author = "蒋德文";
    private static final String url = "jdbc:mysql://127.0.0.1:3306/test?serverTimezone=GMT%2b8&zeroDateTimeBehavior=convertToNull&characterEncoding=utf8&useSSL=false";
    private static final String user = "root";
    private static final String password = "root";

    protected static List<String> getTables(String tables) {
        return "all".equals(tables) ? Collections.emptyList() : Arrays.asList(tables.split(","));
    }

    public static void main(String[] args) {
        FastAutoGenerator.create(url, user, password)
                // 全局配置
                .globalConfig((scanner, builder) ->
                        builder.author(author)
                                .outputDir(projectPath))
                // 包配置
                .packageConfig((scanner, builder) -> builder.parent("com.chinacreator.hosp.cda"))
                // 策略配置
                .strategyConfig((scanner, builder) -> builder.addInclude(getTables(scanner.apply("请输入表名，多个英文逗号分隔？所有输入" +
                                " all")))
                        .controllerBuilder().enableRestStyle().enableHyphenStyle()
                        .entityBuilder().enableLombok()
                        .build())
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();
    }

}