package com.mdh.gmall.oms.sevice.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mdh.gmall.oms.entity.OrderOperateHistory;
import com.mdh.gmall.oms.mapper.OrderOperateHistoryMapper;
import com.mdh.gmall.oms.service.OrderOperateHistoryService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单操作历史记录 服务实现类
 * </p>
 *
 * @author mdh
 * @since 2019-12-11
 */
@Service
public class OrderOperateHistoryServiceImpl extends ServiceImpl<OrderOperateHistoryMapper, OrderOperateHistory> implements OrderOperateHistoryService {

}
