package com.mdh.gmall.ums.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mdh.gmall.ums.entity.MemberLevel;
import com.mdh.gmall.ums.mapper.MemberLevelMapper;
import com.mdh.gmall.ums.service.MemberLevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 * 会员等级表 服务实现类
 * </p>
 *
 * @author mdh
 * @since 2019-12-11
 */
@Component
@Service
public class MemberLevelServiceImpl extends ServiceImpl<MemberLevelMapper, MemberLevel> implements MemberLevelService {

    @Autowired
    MemberLevelMapper memberLevelMapper;

    @Override
    public List<MemberLevel> memberLevelList(String defaultStatus) {
        QueryWrapper queryWrapper = new QueryWrapper<MemberLevel>();
        queryWrapper.eq("default_status" , defaultStatus);
        return memberLevelMapper.selectList(queryWrapper);
    }
}
