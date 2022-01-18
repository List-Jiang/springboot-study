package com.jdw.sys.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.lang.String;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.baomidou.mybatisplus.annotation.TableId;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * 
 * </p>
 *
 * @author jdw
 * @since 2020-05-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Demo对象", description="")
public class Demo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
    * 主键ID
    */
    @ApiModelProperty(value = "主键ID")
    @TableId("id")
    private String id;

    /**
    * 姓名
    */
    @ApiModelProperty(value = "姓名")
    private String name;

    /**
    * 关键词
    */
    @ApiModelProperty(value = "关键词")
    private String keyWord;

    /**
    * 打卡时间
    */
    @ApiModelProperty(value = "打卡时间")
    private LocalDateTime punchTime;

    /**
    * 工资
    */
    @ApiModelProperty(value = "工资")
    private BigDecimal salaryMoney;

    /**
    * 奖金
    */
    @ApiModelProperty(value = "奖金")
    private Double bonusMoney;

    /**
    * 性别 {男:1,女:2}
    */
    @ApiModelProperty(value = "性别 {男:1,女:2}")
    private String sex;

    /**
    * 年龄
    */
    @ApiModelProperty(value = "年龄")
    private Integer age;

    /**
    * 生日
    */
    @ApiModelProperty(value = "生日")
    private LocalDate birthday;

    /**
    * 邮箱
    */
    @ApiModelProperty(value = "邮箱")
    private String email;

    /**
    * 个人简介
    */
    @ApiModelProperty(value = "个人简介")
    private String content;

    /**
    * 创建人
    */
    @ApiModelProperty(value = "创建人")
    private String createBy;

    /**
    * 创建时间
    */
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    /**
    * 修改人
    */
    @ApiModelProperty(value = "修改人")
    private String updateBy;

    /**
    * 修改时间
    */
    @ApiModelProperty(value = "修改时间")
    private LocalDateTime updateTime;

    /**
    * 所属部门编码
    */
    @ApiModelProperty(value = "所属部门编码")
    private String sysOrgCode;

    /**
    * 有效状态，0无效，1，有效
    */
    @TableLogic
    @ApiModelProperty(value = "有效状态，0无效，1，有效")
    private Integer status;

    public static String test03(String s1, String s2, String s3) {
        String s = s1 + s2 + s3;
        return s;
    }
}
