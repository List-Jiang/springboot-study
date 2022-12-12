package com.jdw.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.jdw.springboot.enums.SexEnum;
import com.jdw.springboot.enums.StatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * <p>
 * </p>
 * @author jdw
 * @since 2020-05-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(name = "User对象")
public class User implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @Schema(name = "主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 姓名
     */
    @Schema(name = "姓名")
    private String name;

    /**
     * 年龄
     */
    @Schema(name = "年龄")
    private Integer age;

    /**
     * 邮箱
     */
    @Schema(name = "邮箱")
    private String email;

    /**
     * 性别
     */
    @Schema(name = "性别")
    private SexEnum sex;

    /**
     * 有效状态
     */
    @Schema(name = "有效状态")
    private StatusEnum status;

    /**
     * 登录账号
     */
    @Schema(name = "登录账号")
    private String account;

    /**
     * 登录密码
     */
    @Schema(name = "登录密码")
    private String password;

    /**
     * 创建时间
     */
    @Schema(name = "创建时间")
    private LocalDateTime createDate;

    /**
     * 修改时间
     */
    @Schema(name = "修改时间")
    private LocalDateTime updateDate;

    /**
     * 角色集合
     */
    @TableField(exist = false)
    private Set<Role> roles;
}
