package com.mdh.gmall.pms.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mdh.gmall.pms.entity.SkuStock;
import com.mdh.gmall.pms.mapper.SkuStockMapper;
import com.mdh.gmall.pms.service.SkuStockService;
import org.springframework.stereotype.Component;

/**
 * <p>
 * sku的库存 服务实现类
 * </p>
 *
 * @author mdh
 * @since 2019-12-11
 */
@Component
@Service
public class SkuStockServiceImpl extends ServiceImpl<SkuStockMapper, SkuStock> implements SkuStockService {

}
