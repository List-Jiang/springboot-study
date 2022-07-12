package com.jdw.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jdw.sys.entity.RolePermissions;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author jdw
 * @since 2020-06-03
 */
public interface RolePermissionsMapper extends BaseMapper<RolePermissions> {
    @Select("select * from role_ermissions where user_id = #{roleId}")
    List<RolePermissions> getRolePermissionsByRoleId(Long roleId);
}
