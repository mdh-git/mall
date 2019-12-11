package com.mdh.gmall.ums.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mdh.gmall.ums.entity.Role;
import com.mdh.gmall.ums.mapper.RoleMapper;
import com.mdh.gmall.ums.service.RoleService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 后台用户角色表 服务实现类
 * </p>
 *
 * @author mdh
 * @since 2019-12-11
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

}
