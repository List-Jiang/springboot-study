package com.jdw.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jdw.sys.entity.Role;
import com.jdw.sys.mapper.RoleMapper;
import com.jdw.sys.service.IRoleService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 * @author jdw
 * @since 2020-05-30
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

}
