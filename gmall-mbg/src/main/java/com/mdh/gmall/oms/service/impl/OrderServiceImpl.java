package com.mdh.gmall.oms.service.impl;

import com.mdh.gmall.oms.entity.Order;
import com.mdh.gmall.oms.mapper.OrderMapper;
import com.mdh.gmall.oms.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单表 服务实现类
 * </p>
 *
 * @author mdh
 * @since 2019-12-11
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

}
