package com.mdh.gmall.ums.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mdh.gmall.ums.entity.Admin;
import com.mdh.gmall.ums.mapper.AdminMapper;
import com.mdh.gmall.ums.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

/**
 * <p>
 * 后台用户表 服务实现类
 * </p>
 *
 * @author mdh
 * @since 2019-12-11
 */
@Component
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {

    @Autowired
    AdminMapper adminMapper;

    @Override
    public Admin login(String name, String password) {

        // 对密码进行加密，使用spring提供的工具类DigestUtils
        String stringBuilder = DigestUtils.md5DigestAsHex(password.getBytes());

        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", name);
        queryWrapper.eq("password", stringBuilder);
        Admin admin = adminMapper.selectOne(queryWrapper);
        return admin;
    }

    /**
     * 根据username获取用户详情
     * @param userName
     * @return
     */
    @Override
    public Admin getUserInfo(String userName) {
        return adminMapper.selectOne(new QueryWrapper<Admin>().eq("username", userName));
    }


}
