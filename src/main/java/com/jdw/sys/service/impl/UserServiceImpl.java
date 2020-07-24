package com.jdw.sys.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.kotlin.KtQueryWrapper;
import com.jdw.sys.entity.*;
import com.jdw.sys.mapper.*;
import com.jdw.sys.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jdw
 * @since 2020-05-27
 */
@Service
//配置独立数据源
//@DS("test_2")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Autowired(required = false)
    UserMapper userMapper;

    @Autowired
    UserRoleMapper userRoleMapper;

    @Autowired
    RoleMapper roleMapper;

    @Autowired
    RolePermissionsMapper rolePermissionsMapper;

    @Autowired
    PermissionsMapper permissionsMapper;

    @Override
    public List<String> AllAccount() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        List<User> users = userMapper.selectList(wrapper);
        List<String> account = new ArrayList<>();
        users.forEach(st->{
            account.add(st.getAccount());
        });
        return account;
    }

    /**
     * 通过用户id获取用户信息
     * @param id
     * @return
     */
    public User getUserById(@Param("id") Long id){
        //获取一个用户
        User user = userMapper.selectById(id);
        //获取用户角色关系 list
        List<UserRole> userRoleList = userRoleMapper.getUserRoleMapperByUserId(user.getId());
        //获取角色集合
        Set<Role> roleSet = new HashSet<>();
        //用户角色关系 迭代
        userRoleList.forEach(userRole -> {
            //获取一个角色
            Role role = roleMapper.selectById(userRole.getRoleId());
            //新建权限集合
            Set<Permissions> permissionsSet = new HashSet<>();
            //获取角色权限关系 list
            List<RolePermissions> rolePermissionsList = rolePermissionsMapper.getRolePermissionsByRoleId(role.getId());
            //迭代角色权限关系
            rolePermissionsList.forEach(rolePermissions -> {
                //获取一个权限
                Permissions permissions = permissionsMapper.selectById(rolePermissions.getPermissionsId());
                //添加权限对象至权限集合
                permissionsSet.add(permissions);
            });
            //角色设置权限集合
            role.setPermissions(permissionsSet);
            //添加角色对象至角色集合
            roleSet.add(role);
        });
        //用户设置角色集合
        user.setRoles(roleSet);
        return user;
    }

    public List<User> getUserByName(@Param("name") String name){
        return userMapper.getUserByName(name);
    }

    @Override
    public User getUserByAccount(String account) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(User::getAccount, account);
        User user = getOne(wrapper);
        return user;
    }

}
