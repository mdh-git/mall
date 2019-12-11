package com.mdh.gmall.sms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mdh.gmall.sms.entity.CouponHistory;
import com.mdh.gmall.sms.mapper.CouponHistoryMapper;
import com.mdh.gmall.sms.service.CouponHistoryService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 优惠券使用、领取历史表 服务实现类
 * </p>
 *
 * @author mdh
 * @since 2019-12-11
 */
@Service
public class CouponHistoryServiceImpl extends ServiceImpl<CouponHistoryMapper, CouponHistory> implements CouponHistoryService {

}
