package com.mdh.gmall.oms.sevice.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mdh.gmall.oms.entity.OrderItem;
import com.mdh.gmall.oms.mapper.OrderItemMapper;
import com.mdh.gmall.oms.service.OrderItemService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单中所包含的商品 服务实现类
 * </p>
 *
 * @author mdh
 * @since 2019-12-11
 */
@Service
public class OrderItemServiceImpl extends ServiceImpl<OrderItemMapper, OrderItem> implements OrderItemService {

}
