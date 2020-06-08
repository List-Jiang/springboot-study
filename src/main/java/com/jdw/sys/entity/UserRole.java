package com.jdw.sys.entity;

import java.io.Serializable;
import java.lang.String;
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
 * @since 2020-05-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="UserRole对象", description="")
public class UserRole implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
    * 用户id
    */
    @ApiModelProperty(value = "用户id")
    @TableId("user_id")
    private Long userId;

    /**
    * 角色id
    */
    @ApiModelProperty(value = "角色id")
    private Long roleId;


}
