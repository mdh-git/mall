package com.mdh.gmall.ums.service.impl;

import com.mdh.gmall.ums.entity.Admin;
import com.mdh.gmall.ums.mapper.AdminMapper;
import com.mdh.gmall.ums.service.AdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 后台用户表 服务实现类
 * </p>
 *
 * @author mdh
 * @since 2019-12-11
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {

}
