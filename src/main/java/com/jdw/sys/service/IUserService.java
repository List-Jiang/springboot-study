package com.jdw.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jdw.sys.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author jdw
 * @since 2020-05-27
 */
@CacheConfig(cacheNames = "Users")
public interface IUserService extends IService<User> {
    List<String> AllAccount();

    User getUserById(@Param("id") Long id);

    @Async
    CompletableFuture<List<User>> getUserByName(@Param("name") String name);

    @Cacheable
    User getUserByAccount(@Param("account") String account);
}
