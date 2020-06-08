package com.jdw.sys.service.impl;

import com.jdw.sys.entity.Demo;
import com.jdw.sys.mapper.DemoMapper;
import com.jdw.sys.service.IDemoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jdw
 * @since 2020-05-26
 */
@Service
public class DemoServiceImpl extends ServiceImpl<DemoMapper, Demo> implements IDemoService {

}
