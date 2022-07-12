package com.jdw.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

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
@Schema(name = "Demo对象")
public class Demo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @Schema(name = "主键ID")
    @TableId("id")
    private String id;

    /**
     * 姓名
     */
    @Schema(name = "姓名")
    private String name;

    /**
     * 关键词
     */
    @Schema(name = "关键词")
    private String keyWord;

    /**
     * 打卡时间
     */
    @Schema(name = "打卡时间")
    private LocalDateTime punchTime;

    /**
     * 工资
     */
    @Schema(name = "工资")
    private BigDecimal salaryMoney;

    /**
     * 奖金
     */
    @Schema(name = "奖金")
    private Double bonusMoney;

    /**
     * 性别 {男:1,女:2}
     */
    @Schema(name = "性别 {男:1,女:2}")
    private String sex;

    /**
     * 年龄
     */
    @Schema(name = "年龄")
    private Integer age;

    /**
     * 生日
     */
    @Schema(name = "生日")
    private LocalDate birthday;

    /**
     * 邮箱
     */
    @Schema(name = "邮箱")
    private String email;

    /**
     * 个人简介
     */
    @Schema(name = "个人简介")
    private String content;

    /**
     * 创建人
     */
    @Schema(name = "创建人")
    private String createBy;

    /**
     * 创建时间
     */
    @Schema(name = "创建时间")
    private LocalDateTime createTime;

    /**
     * 修改人
     */
    @Schema(name = "修改人")
    private String updateBy;

    /**
     * 修改时间
     */
    @Schema(name = "修改时间")
    private LocalDateTime updateTime;

    /**
     * 所属部门编码
     */
    @Schema(name = "所属部门编码")
    private String sysOrgCode;

    /**
     * 有效状态，0无效，1，有效
     */
    @TableLogic
    @Schema(name = "有效状态，0无效，1，有效")
    private Integer status;

    public static String test03(String s1, String s2, String s3) {
        String s = s1 + s2 + s3;
        return s;
    }
}
