package com.jdw.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jdw.sys.entity.Permissions;
import com.jdw.sys.mapper.PermissionsMapper;
import com.jdw.sys.service.IPermissionsService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author jdw
 * @since 2020-06-03
 */
@Service
public class PermissionsServiceImpl extends ServiceImpl<PermissionsMapper, Permissions> implements IPermissionsService {

}
