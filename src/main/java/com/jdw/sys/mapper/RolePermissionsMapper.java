package com.jdw.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jdw.sys.entity.RolePermission;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jdw
 * @since 2020-06-03
 */
public interface RolePermissionsMapper extends BaseMapper<RolePermission> {
    @Select("select * from role_ermissions where user_id = #{roleId}")
    List<RolePermission> getRolePermissionsByRoleId(Long roleId);
}
