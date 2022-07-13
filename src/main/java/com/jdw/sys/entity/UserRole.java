package com.jdw.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * <p>
 * </p>
 * @author jdw
 * @since 2020-05-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(name = "UserRole对象")
public class UserRole implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    @Schema(name = "用户id")
    @TableId("user_id")
    private Long userId;

    /**
     * 角色id
     */
    @Schema(name = "角色id")
    private Long roleId;


}
