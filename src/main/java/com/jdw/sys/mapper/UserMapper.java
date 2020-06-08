package com.jdw.sys.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.jdw.sys.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jdw
 * @since 2020-05-27
 */
//@DS("test_2")
public interface UserMapper extends BaseMapper<User> {

    @Select("select * from user where name = #{name}")
    List<User> getUserByName(@Param("name") String name);

}
