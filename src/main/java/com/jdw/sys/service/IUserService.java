package com.jdw.sys.service;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.jdw.sys.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jdw
 * @since 2020-05-27
 */
@CacheConfig(cacheNames = "Users")
public interface IUserService extends IService<User> {
    List<String> AllAccount();

    User getUserById(@Param("id") Long id);

    List<User> getUserByName(@Param("name") String name);
    @Cacheable
    User getUserByAccount(@Param("account") String account);
}
