package com.jdw.sys.service;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.jdw.sys.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jdw
 * @since 2020-05-27
 */
public interface IUserService extends IService<User> {
    User getUserById(@Param("id") Long id);

    List<User> getUserByName(@Param("name") String name);

    User getUserByAccount(@Param("account") String account);
}
