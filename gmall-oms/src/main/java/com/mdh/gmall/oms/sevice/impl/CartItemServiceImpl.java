package com.mdh.gmall.oms.sevice.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mdh.gmall.oms.entity.CartItem;
import com.mdh.gmall.oms.mapper.CartItemMapper;
import com.mdh.gmall.oms.service.CartItemService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 购物车表 服务实现类
 * </p>
 *
 * @author mdh
 * @since 2019-12-11
 */
@Service
public class CartItemServiceImpl extends ServiceImpl<CartItemMapper, CartItem> implements CartItemService {

}
