package com.jdw.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.lang.String;
import java.util.Set;

import com.jdw.springboot.enums.SexEnum;
import com.jdw.springboot.enums.StatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.baomidou.mybatisplus.annotation.TableId;

/**
 * <p>
 * 
 * </p>
 *
 * @author jdw
 * @since 2020-05-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="User对象", description="")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
    * 主键ID
    */
    @ApiModelProperty(value = "主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
    * 姓名
    */
    @ApiModelProperty(value = "姓名")
    private String name;

    /**
    * 年龄
    */
    @ApiModelProperty(value = "年龄")
    private Integer age;

    /**
    * 邮箱
    */
    @ApiModelProperty(value = "邮箱")
    private String email;

    /**
    * 性别
    */
    @ApiModelProperty(value = "性别")
    private SexEnum sex;

    /**
    * 有效状态
    */
    @ApiModelProperty(value = "有效状态")
    private StatusEnum status;

    /**
     * 登录账号
     */
    @ApiModelProperty(value = "登录账号")
    private String account;

    /**
     * 登录密码
     */
    @ApiModelProperty(value = "登录密码")
    private String password;

    /**
     * 角色集合
     */
    @TableField(exist = false)
    private Set<Role> roles;
}
