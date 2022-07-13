package com.jdw.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * <p>
 * </p>
 * @author jdw
 * @since 2020-06-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Schema(name = "RolePermissions对象")
public class RolePermissions implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId("id")
    private Long id;

    /**
     * 角色id
     */
    @Schema(name = "角色id")
    private Long roleId;

    /**
     * 权限id
     */
    @Schema(name = "权限id")
    private Long permissionsId;


}
