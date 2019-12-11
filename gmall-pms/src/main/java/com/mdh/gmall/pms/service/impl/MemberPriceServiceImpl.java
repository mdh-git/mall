package com.mdh.gmall.pms.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mdh.gmall.pms.entity.MemberPrice;
import com.mdh.gmall.pms.mapper.MemberPriceMapper;
import com.mdh.gmall.pms.service.MemberPriceService;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 商品会员价格表 服务实现类
 * </p>
 *
 * @author mdh
 * @since 2019-12-11
 */
@Component
@Service
public class MemberPriceServiceImpl extends ServiceImpl<MemberPriceMapper, MemberPrice> implements MemberPriceService {

}
