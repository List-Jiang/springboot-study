package com.jdw.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jdw.sys.entity.UserRole;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 * @author jdw
 * @since 2020-05-30
 */
public interface UserRoleMapper extends BaseMapper<UserRole> {
    @Select("select * from user_role where user_id = #{userId}")
    List<UserRole> getUserRoleMapperByUserId(Long userId);
}
