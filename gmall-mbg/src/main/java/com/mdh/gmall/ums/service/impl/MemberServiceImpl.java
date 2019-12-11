package com.mdh.gmall.ums.service.impl;

import com.mdh.gmall.ums.entity.Member;
import com.mdh.gmall.ums.mapper.MemberMapper;
import com.mdh.gmall.ums.service.MemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author mdh
 * @since 2019-12-11
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {

}
